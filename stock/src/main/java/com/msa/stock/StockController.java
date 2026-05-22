package com.msa.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @PatchMapping("{userid}")
    ResponseEntity<StockDTO> purchase(@RequestBody StockPurchaseDTO dto) {
        var stock = service.purchase(dto);
        return ResponseEntity.ok(stock);
    }
}
