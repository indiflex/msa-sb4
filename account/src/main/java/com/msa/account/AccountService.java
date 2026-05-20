package com.msa.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public AccountDTO createAccount(AccountCreateDTO dto) {
        Account toCreateAccount = Account.builder()
                .userid(dto.getUserid())
                .accountName(dto.getUsername() + "주식계좌")
                .accountNumber("101-" + dto.getUserid())
                .passwd(passwordEncoder.encode(dto.getPasswd()))
                .balance(BigDecimal.valueOf(10000))
                .build();

        Account account = repository.save(toCreateAccount);
        return mapper.toDTO(account);
    }

    @Transactional
    public Object deposit(Long userid, BigDecimal amount) {
        Account account = repository.findByUseridForUpdate(userid).orElseThrow(() -> new IllegalArgumentException("NotFoundAccount"));

        account.setBalance(account.getBalance().add(amount));
        repository.save(account);
    }
}
