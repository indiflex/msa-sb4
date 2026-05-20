package com.msa.stock;

import jakarta.persistence.Column;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Data
public class StockDTO {
    private Long id;
    private String stockName;
    private int cnt;
    private BigDecimal price;
    private Long userid;
}
