package com.msa.stock;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockMapper {
    StockDTO toDTO(Stock stock);

    Stock toEntity(StockDTO dto);

    Stock toPurchase(StockPurchaseDTO dto);
}
