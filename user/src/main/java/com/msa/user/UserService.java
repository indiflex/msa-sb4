package com.msa.user;

import com.msa.event.StockEvent;
import com.msa.event.UserEvent;
import com.msa.user.client.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private final Client client; // RestClient
    private final AccountClient accountClient; // Feign
    private final StockClient stockClient;  // Feign

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    @KafkaListener(topics = "stock-purchase", groupId = "user-service")
    @Transactional
    public UserDTO addPointByEvent(StockEvent event) {
        User user = repository.findByIdForUpdate(event.getUserid());
        user.addPoint(event.getCnt() * 100);
        return mapper.toDTO(repository.save(user));
    }

    @Transactional
    public UserDTO registAsync(UserRegistDTO dto) {
        User user = mapper.toEntity(dto);
        user.setPasswd(passwordEncoder.encode(dto.getPasswd()));
        user.addRole(UserRole.ROLE_USER);
        User newer = repository.save(user);

        UserEvent event = new UserEvent("REGIST", newer.getId(), newer.getName(), dto.getAccountPasswd());
        kafkaTemplate.send("user-regist", event);

        return mapper.toDTO(newer);
    }

    //    @Transactional
    // Saga pattern
    public UserDTO regist(UserRegistDTO dto) {
//        boolean didRegistUser = false;
        Long userid = null;
        boolean didCreateAccount = false;

        try {
            User user = mapper.toEntity(dto);
            user.setPasswd(passwordEncoder.encode(dto.getPasswd()));
            user.addRole(UserRole.ROLE_USER);

            User newer = repository.save(user);
            userid = newer.getId();
//            didRegistUser = true;

            accountClient.createAccount(new AccountCreateDTO(dto.getName(), dto.getAccountPasswd(), userid));
            didCreateAccount = true;

            stockClient.createStock(new StockDTO(1, BigDecimal.valueOf(1000), userid));
            return mapper.toDTO(user);
        } catch (Exception e) {
//            if (didRegistUser) {
            if (userid != null) {
                repository.deleteById(userid);
            }

            if (didCreateAccount) {
                accountClient.deleteAccount(userid);
            }

            throw e;
        }
    }

    public UserDTO getUser(Long id) {
        User user = repository.findById(id).orElseThrow();
        UserDTO dto = mapper.toDTO(user);
        dto.setPasswd("");
        dto.setAccount(client.getAccountInfo(id));
        dto.setStock(client.getStockInfo(id));

        return dto;
    }

    @Transactional
    public UserDTO addPoint(AddPointDTO dto) {
        User user = repository.findByIdForUpdate(dto.getUserid());
        user.addPoint(dto.getCnt() * 100);
        return mapper.toDTO(repository.save(user));
    }
}
