package com.msa.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public UserDTO regist(UserRegistDTO dto) {
        return mapper.toDTO(repository.save(mapper.toEntity(dto)));
    }

    public UserDTO getUser(Long id) {
        User user = repository.findById(id).orElseThrow();
        user.setPasswd("");
        return mapper.toDTO(user);
    }
}
