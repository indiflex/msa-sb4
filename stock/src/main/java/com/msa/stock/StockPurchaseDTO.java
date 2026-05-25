package com.msa.stock;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class StockPurchaseDTO {
    @Positive
    private Integer cnt;

    @Positive
    private BigDecimal price;

    @NotNull
    private Long userid;

    private String accountPasswd;
}
