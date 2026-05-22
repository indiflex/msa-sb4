package com.msa.stock.client;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class WithdrawalDTO {
    private BigDecimal amount;
    private Long userid;
    private String passwd;
}
