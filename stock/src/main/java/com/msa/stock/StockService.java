package com.msa.stock;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository repository;
    private final StockMapper mapper;

    public StockDTO createStock(StockPurchaseDTO dto) {
        Stock stock = repository.save(mapper.toPurchase(dto));
        return mapper.toDTO(stock);
    }

    @Transactional
    public StockDTO purchase(StockPurchaseDTO dto) {
        Stock stock = repository.findByUseridForUpdate(dto.getUserid()).orElseThrow(() -> new IllegalArgumentException("NotFoundStock"));

        stock.setCnt(stock.getCnt() + dto.getCnt());
        var newPrice = dto.getPrice().multiply(BigDecimal.valueOf(dto.getCnt()));
        stock.setPrice(stock.getPrice().add(newPrice));
        return mapper.toDTO(repository.save(stock));
    }

    public StockDTO getStock(Long userid) {
        return mapper.toDTO(repository.findByUserid(userid).orElseThrow());
    }
}
