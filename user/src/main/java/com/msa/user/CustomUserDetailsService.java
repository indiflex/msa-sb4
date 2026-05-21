package com.msa.user;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@NullMarked
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("*** Service.loadUserByUsername.email=" + username);
        User user = repository.getWithRoles(username);
        System.out.println("user = " + user);
        if (user == null) {
            System.out.println("User not found:" + username);
            throw new UsernameNotFoundException(username + " is Not Found!");
        }

//        return mapper.toDTO(user);
        UserDTO dto = mapper.toDTO(user);
        System.out.println("dtoxxx = " + dto);
        return dto;
    }
}
