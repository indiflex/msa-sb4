package com.msa.user;

import com.msa.user.client.*;
import lombok.RequiredArgsConstructor;
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

    //    @Transactional
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
}
