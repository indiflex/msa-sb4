package com.msa.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/stocks")
@RequiredArgsConstructor
public class StockInternalController {
    private final StockService service;

    @PostMapping()
    ResponseEntity<StockDTO> createStock(@RequestBody StockPurchaseDTO dto) {
        var stock = service.createStock(dto);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("{userid}")
    ResponseEntity<StockDTO> getStock(@PathVariable Long userid) {
        var stock = service.getStock(userid);
        return ResponseEntity.ok(stock);
    }

}
