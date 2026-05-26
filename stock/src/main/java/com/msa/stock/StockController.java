package com.msa.stock;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stocks")
@RequiredArgsConstructor
public class StockController {
    private final StockService service;

    @PostMapping("purchase")
    ResponseEntity<StockDTO> purchase(@RequestHeader("X-User-Id") Long userid, @RequestBody StockPurchaseDTO dto) {
        dto.setUserid(userid);
        var stock = service.purchase(dto);
        return ResponseEntity.ok(stock);
    }

    @PatchMapping("async/{userid}")
    ResponseEntity<StockDTO> purchaseAsync(@RequestBody StockPurchaseDTO dto) {
        var stock = service.purchaseAsync(dto);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("stock-info")
    ResponseEntity<StockDTO> getStock(@RequestHeader("X-User-Id") Long userid) {
        var stock = service.getStock(userid);
        return ResponseEntity.ok(stock);
    }
}
