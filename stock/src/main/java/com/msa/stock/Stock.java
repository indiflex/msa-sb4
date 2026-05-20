package com.msa.stock;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int unsigned")
    private Long id;

    @Column(nullable = false)
    private String stockName;

    @ColumnDefault("0")
    private int cnt;

    @ColumnDefault("0")
    private BigDecimal price;

    @Column(nullable = false)
    private Long userid;
}
