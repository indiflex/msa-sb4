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
    public AccountDTO deposit(Long userid, BigDecimal amount) {
        Account account = getAccount(userid);
        account.setBalance(account.getBalance().add(amount));
        return mapper.toDTO(repository.save(account));
    }

    @Transactional
    public AccountDTO withdrawal(AccountWithdrawalDTO dto) {
        Account account = getAccount(dto.getUserid());

        if (!passwordEncoder.matches(dto.getPasswd(), account.getPasswd()))
            throw new IllegalArgumentException("Not matched password!");

        var newBalance = account.getBalance().subtract(dto.getAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("Not enough balance!");

        account.setBalance(newBalance);
        return mapper.toDTO(repository.save(account));
    }

    private Account getAccount(Long userid) {
        return repository.findByUseridForUpdate(userid).orElseThrow(() -> new IllegalArgumentException("NotFoundAccount"));
    }

}
