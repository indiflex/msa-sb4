package com.msa.account;

import com.msa.event.UserEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repository;
    private final AccountMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @KafkaListener(topics = "user-regist", groupId = "account-service")
    public void createAccountByUserRegist(UserEvent event) {
        if (event == null) return;
        AccountCreateDTO dto = new AccountCreateDTO(event.getUsername(), event.getAccountPasswd(), event.getUserid());

        this.createAccount(dto);
    }

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
        System.out.println("dto = " + dto);
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

    public AccountDTO getAccountInfo(Long userid) {
        Account account = repository.findByUserid(userid).orElseThrow(() -> new IllegalArgumentException("NotFoundAccount"));

        return mapper.toDTO(account);
    }

    @Transactional
    public void deleteAccount(Long userid) {
        Account account = repository.findByUseridForUpdate(userid).orElseThrow(() -> new IllegalArgumentException("NotFoundAccount"));
        repository.deleteById(account.getId());
    }
}
