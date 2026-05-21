package com.msa.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @PostMapping()
    ResponseEntity<StockDTO> createStock(@RequestBody StockPurchaseDTO dto) {
        var stock = service.createStock(dto);
        return ResponseEntity.ok(stock);
    }

    @PatchMapping("{userid}")
    ResponseEntity<StockDTO> purchase(@RequestBody StockPurchaseDTO dto) {
        var stock = service.purchase(dto);
        return ResponseEntity.ok(stock);
    }
}
