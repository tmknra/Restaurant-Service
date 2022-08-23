package com.example.userservice.mapper;

import com.example.userservice.dto.in.UserInDto;
import com.example.userservice.dto.out.UserOutDto;
import com.example.userservice.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract UserOutDto userEntityToUserOutDto(UserEntity userEntity);

    @Mapping(target = "id", ignore = true)
    public abstract UserEntity userInDtoToUserEntity(UserInDto userInDto);
}
