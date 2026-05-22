package com.msa.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "stock-service", url = "${client.stock}")
public interface StockClient {
    @GetMapping("/interal/stocks/{userid}")
    StockDTO getStock(@PathVariable Long userid);

    @PostMapping("/internal/stocks")
    StockDTO createStock(@RequestBody StockDTO dto);
}
