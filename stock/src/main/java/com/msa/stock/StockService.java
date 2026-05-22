package com.msa.stock;

import com.msa.stock.client.AccountClient;
import com.msa.stock.client.AddPointDTO;
import com.msa.stock.client.UserClient;
import com.msa.stock.client.WithdrawalDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockMapper mapper;

    private final AccountClient accountClient;
    private final UserClient userClient;

    public StockDTO createStock(StockPurchaseDTO dto) {
        Stock stock = repository.save(mapper.toPurchase(dto));
        return mapper.toDTO(stock);
    }

    //    @Transactional
    public StockDTO purchase(StockPurchaseDTO dto) {
        // 계좌 출금!
        boolean didWithdrawal = false;
        BigDecimal amount = null;
        boolean didPurchase = false;
        Stock stock = null;
        BigDecimal newPrice = null;
        try {
            amount = dto.getPrice().multiply(BigDecimal.valueOf(dto.getCnt()));
            accountClient.withdrawl(dto.getUserid(), new WithdrawalDTO(amount, dto.getUserid(), dto.getAccountPasswd()));
            didWithdrawal = true;

            // 주식 체결
            stock = repository.findByUseridForUpdate(dto.getUserid()).orElseThrow(() -> new IllegalArgumentException("NotFoundStock"));

            stock.setCnt(stock.getCnt() + dto.getCnt());
            newPrice = dto.getPrice().multiply(BigDecimal.valueOf(dto.getCnt()));
            stock.setPrice(stock.getPrice().add(newPrice));
            StockDTO newer = mapper.toDTO(repository.save(stock));
            didPurchase = true;

            // 활동포인트 지급
            userClient.addPoint(new AddPointDTO(dto.getUserid(), dto.getCnt()));

            return newer;
        } catch (Exception e) {
            if (didWithdrawal) {
                accountClient.deposit(dto.getUserid(), amount);
            }
            if (didPurchase) {
                stock.setCnt(stock.getCnt() - dto.getCnt());
                stock.setPrice(stock.getPrice().subtract(newPrice));
                repository.save(stock);
            }

            throw e;
        }
    }

    public StockDTO getStock(Long userid) {
        return mapper.toDTO(repository.findByUserid(userid).orElseThrow());
    }
}
