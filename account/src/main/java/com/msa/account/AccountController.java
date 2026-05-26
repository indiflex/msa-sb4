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

    @GetMapping("account-info")
    ResponseEntity<AccountDTO> getAccount(@RequestHeader("X-User-Id") Long userid) {
        var account = service.getAccountInfo(userid);
        return ResponseEntity.ok(account);
    }
}
