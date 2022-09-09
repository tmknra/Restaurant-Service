package com.example.user_service.mapper;


import com.example.user_service.dto.in.RoleInDto;
import com.example.user_service.dto.out.RoleOutDto;
import com.example.user_service.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    @Mapping(target = "id", ignore = true)
    public abstract RoleEntity roleInDtoToRole(RoleInDto roleInDto);

    public abstract RoleOutDto roleToRoleOutDto(RoleEntity role);

}
