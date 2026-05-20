package com.msa.account;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "passwd", ignore = true)
    AccountDTO toDTO(Account account);

    Account toEntity(AccountDTO dto);
}
