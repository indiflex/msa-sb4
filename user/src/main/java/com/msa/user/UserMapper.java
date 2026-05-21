package com.msa.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roleNames", expression = "java(user.getRoles().stream().map(Enum::name).toList())")
//    @Mapping(target = "passwd", ignore = true)
    UserDTO toDTO(User user);

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDTO dto);

    @Mapping(target = "roles", ignore = true)
    User toEntity(UserRegistDTO dto);
}
