package com.msa.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockEvent {
    private String status;
    private Long userid;
    private Integer cnt;
}
