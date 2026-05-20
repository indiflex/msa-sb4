package com.msa.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @PostMapping
    ResponseEntity<AccountDTO> createAccount(@RequestBody AccountCreateDTO dto) {
        var account = service.createAccount(dto);
        return ResponseEntity.ok(account);
    }

    @PutMapping("{userid}")
    ResponseEntity<AccountDTO> deposit(@PathVariable("userid") Long userid, @RequestParam("amount") BigDecimal amount) {
        var account = service.deposit(userid, amount);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("{userid}")
    ResponseEntity<AccountDTO> withdrawal(@RequestBody AccountWithdrawalDTO dto) {
        var account = service.withdrawal(dto);
        return ResponseEntity.ok(account);
    }
}
