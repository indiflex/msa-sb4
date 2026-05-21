package com.msa.user;

import com.msa.user.client.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final Client client;

    public UserDTO regist(UserRegistDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    public UserDTO getUser(Long id) {
        User user = repository.findById(id).orElseThrow();
        UserDTO dto = mapper.toDTO(user);
        dto.setPasswd("");
        dto.setAccount(client.getAccountInfo(id));

        return dto;
    }
}
