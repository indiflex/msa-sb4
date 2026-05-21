package com.msa.user.client;

import java.math.BigDecimal;

public record AccountDTO(
        String accountNumber,
        String accountName,
        BigDecimal balance,
        Long userid
) {
}
