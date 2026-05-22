package com.msa.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class UserInnerController {
    private final UserService service;

    @PatchMapping("addpoint")
    UserDTO addPoint(@RequestBody AddPointDTO dto) {
        return service.addPoint(dto);
    }
}
