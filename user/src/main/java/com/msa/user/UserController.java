package com.msa.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    UserDTO regist(@RequestBody @Validated UserRegistDTO dto) {
        return service.regist(dto);
    }

    @GetMapping("{id}")
    UserDTO getUser(@PathVariable Long id, Authentication auth) {
        UserDTO dto = (UserDTO) auth.getPrincipal();
        if (dto == null || !dto.getId().equals(id))
            throw new JwtException("Not valid user!!");

        return service.getUser(id);
    }

    @GetMapping("hello")
    String hello(Authentication auth) {
        UserDTO dto = (UserDTO) auth.getPrincipal();
        if (dto != null) return "Hello " + dto.getName();
        return "Hello guest";
    }


}
