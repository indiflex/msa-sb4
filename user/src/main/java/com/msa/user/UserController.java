package com.msa.user;

import lombok.RequiredArgsConstructor;
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
    UserDTO getUser(@PathVariable Long id) {
        return service.getUser(id);
    }
}
