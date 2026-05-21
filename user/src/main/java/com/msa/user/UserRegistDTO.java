package com.msa.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
}
