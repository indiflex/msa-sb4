package com.msa.account;

import java.math.BigDecimal;

public record AccountDTO(
        Long id,
        String accountNumber,
        String accountName,
        BigDecimal balance,
        Long userid,
        String passwd
) {
}
