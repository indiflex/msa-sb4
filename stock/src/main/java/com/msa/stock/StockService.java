package com.msa.stock;

import com.msa.event.UserEvent;
import com.msa.stock.client.AccountClient;
import com.msa.stock.client.AddPointDTO;
import com.msa.stock.client.UserClient;
import com.msa.stock.client.WithdrawalDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockMapper mapper;

    private final AccountClient accountClient;
    private final UserClient userClient;

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @KafkaListener(topics = "user-regist", groupId = "stock-service")
    public void createAccountByUserRegist(UserEvent event) {
        if (event == null) return;
        StockPurchaseDTO dto = new StockPurchaseDTO(1, BigDecimal.valueOf(1000), event.getUserid(), event.getAccountPasswd());

        this.createStock(dto);
    }

    public StockDTO createStock(StockPurchaseDTO dto) {
        Stock stock = repository.save(mapper.toPurchase(dto));
        return mapper.toDTO(stock);
    }

    @Transactional
    public StockDTO purchase(StockPurchaseDTO dto) {
        // 계좌 출금!
        boolean didWithdrawal = false;
        BigDecimal amount = null;
        try {
            amount = dto.getPrice().multiply(BigDecimal.valueOf(dto.getCnt()));
            accountClient.withdrawl(dto.getUserid(), new WithdrawalDTO(amount, dto.getUserid(), dto.getAccountPasswd()));
            didWithdrawal = true;

            // 주식 체결
            Stock stock = repository.findByUseridForUpdate(dto.getUserid()).orElseThrow(() -> new IllegalArgumentException("NotFoundStock"));

            stock.setCnt(stock.getCnt() + dto.getCnt());
            BigDecimal newPrice = dto.getPrice().multiply(BigDecimal.valueOf(dto.getCnt()));
            stock.setPrice(stock.getPrice().add(newPrice));
            StockDTO newer = mapper.toDTO(repository.save(stock));

            // 활동포인트 지급
            userClient.addPoint(new AddPointDTO(dto.getUserid(), dto.getCnt()));

            return newer;
        } catch (Exception e) {
            if (didWithdrawal) {
                accountClient.deposit(dto.getUserid(), amount);
            }
//            if (didPurchase) {
//                stock.setCnt(stock.getCnt() - dto.getCnt());
//                stock.setPrice(stock.getPrice().subtract(newPrice));
//                repository.save(stock);
//            }

            throw e;
        }
    }

    public StockDTO getStock(Long userid) {
        return mapper.toDTO(repository.findByUserid(userid).orElseThrow());
    }
}
