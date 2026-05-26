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

    @PutMapping("")
    ResponseEntity<AccountDTO> deposit(@RequestHeader("X-User-Id") Long userid, @RequestParam("amount") BigDecimal amount) {
        var account = service.deposit(userid, amount);
        return ResponseEntity.ok(account);
    }

    @PatchMapping("")
    ResponseEntity<AccountDTO> withdrawal(@RequestHeader("X-User-Id") Long userid, @RequestBody AccountWithdrawalDTO dto) {
        dto.setUserid(userid);
        var account = service.withdrawal(dto);
        return ResponseEntity.ok(account);
    }

    @GetMapping("account-info")
    ResponseEntity<AccountDTO> getAccount(@RequestHeader("X-User-Id") Long userid) {
        var account = service.getAccountInfo(userid);
        return ResponseEntity.ok(account);
    }
}
