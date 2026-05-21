package com.msa.stock;

import java.math.BigDecimal;

public record StockDTO(Long id,
                       Integer cnt,
                       BigDecimal price,
                       Long userid
) {
}
