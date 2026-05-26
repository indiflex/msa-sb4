package com.msa.stock.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "account-service")
public interface AccountClient {
    @PutMapping("/internal/accounts/{userid}")
    void deposit(@PathVariable Long userid, @RequestParam BigDecimal amount);

    @PatchMapping("/internal/accounts/{userid}")
    void withdrawl(@PathVariable Long userid, @RequestBody WithdrawalDTO dto);
}
