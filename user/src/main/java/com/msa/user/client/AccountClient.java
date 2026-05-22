package com.msa.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "${client.account}")
public interface AccountClient {
    @GetMapping("/interal/accounts/{userid}")
    AccountDTO getAccount(@PathVariable Long userid);

    @PostMapping("/internal/accounts")
    AccountDTO createAccount(@RequestBody AccountCreateDTO dto);

    @DeleteMapping("/internal/accounts/{userid}")
    void deleteAccount(@PathVariable Long userid);
}
