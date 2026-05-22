package com.msa.user.client;

public record AccountCreateDTO(String username,
                               String passwd,
                               Long userid
) {
}
