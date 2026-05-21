package com.msa.user.client;

import java.math.BigDecimal;

public record StockDTO(
        Integer cnt,
        BigDecimal price,
        Long userid
) {
}
