package com.msa.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistDTO {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;

    @Size(min = 8, max = 16)
    private String passwd;

    private Integer point;

    @Size(min = 6, max = 6, message = "계좌 비밀번호는 6자리입니다!")
    @Pattern(regexp = "^\\d{6}$", message = "계좌 비밀번호는 6자리 숫자만 가능합니다!")
    private String accountPasswd;
}
