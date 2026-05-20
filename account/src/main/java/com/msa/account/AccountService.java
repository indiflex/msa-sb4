package com.msa.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;

    public AccountDTO createAccount(AccountCreateDTO dto) {
        Account toCreateAccount = Account.builder()
                .accountName(dto.getUsername() + "주식계좌")
                .accountNumber("101" + dto.getUserid())
                .password(dto.getPasswd())
                .build();
        Account account = repository.save(toCreateAccount);
        return mapper.toDTO(account);
    }
}
