package com.example.user_service.mapper;

import com.example.user_service.dto.in.UserInDto;
import com.example.user_service.dto.out.RoleOutDto;
import com.example.user_service.dto.out.UserOutDto;
import com.example.user_service.entity.RoleEntity;
import com.example.user_service.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    private RoleMapper roleMapper;

    public abstract UserOutDto userEntityToUserOutDto(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    public abstract UserEntity userInDtoToUserEntity(UserInDto userInDto);

    RoleOutDto roleToRoleOutDto(RoleEntity role){
        return roleMapper.roleToRoleOutDto(role);
    };
}
