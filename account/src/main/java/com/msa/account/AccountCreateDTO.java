package com.msa.account;

import lombok.Data;

@Data
public class AccountCreateDTO {
    private String username;
    private Long userid;
    private String passwd;
}
