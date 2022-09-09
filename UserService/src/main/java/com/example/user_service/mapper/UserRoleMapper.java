package com.example.user_service.mapper;

import com.example.user_service.dto.in.UserRoleInDto;
import com.example.user_service.dto.out.UserRoleOutDto;
import com.example.user_service.entity.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserRoleMapper {

    @Mapping(target = "id", ignore = true)
    public abstract UserRoleEntity roleInDtoToRole(UserRoleInDto userRoleInDto);

    public abstract UserRoleOutDto roleToRoleOutDto(UserRoleEntity role);

}
