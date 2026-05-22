package com.msa.stock.client;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPointDTO {
    private Long userid;
    private Integer cnt;
}
