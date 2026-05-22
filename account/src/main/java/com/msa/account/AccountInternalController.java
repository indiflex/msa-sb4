package com.msa.account;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/accounts")
@RequiredArgsConstructor
public class AccountInternalController {
    private final AccountService service;

    @PostMapping
    ResponseEntity<AccountDTO> createAccount(@RequestBody AccountCreateDTO dto) {
        var account = service.createAccount(dto);
        return ResponseEntity.ok(account);
    }

    @GetMapping("{userid}")
    ResponseEntity<AccountDTO> getAccount(@PathVariable("userid") Long userid) {
        var account = service.getAccountInfo(userid);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("{userid}")
    ResponseEntity<Void> deleteAccount(@PathVariable Long userid) {
        service.deleteAccount(userid);
        return ResponseEntity.noContent().build();
    }
}
