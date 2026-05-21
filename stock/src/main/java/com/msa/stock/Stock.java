package com.msa.stock;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "int unsigned")
    private Long id;

    @ColumnDefault("0")
    private int cnt;

    @ColumnDefault("0")
    private BigDecimal price;

    @Column(nullable = false, columnDefinition = "int unsigned")
    private Long userid;
}
