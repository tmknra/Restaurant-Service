package com.example.user_service.service;

import com.example.user_service.dto.in.RoleInDto;
import com.example.user_service.dto.in.UserRoleInDto;
import com.example.user_service.dto.out.RoleOutDto;
import com.example.user_service.entity.RoleEntity;
import com.example.user_service.entity.UserRoleEntity;

public interface UsersRolesService {

    RoleEntity createNewRole(RoleInDto role);

    Boolean deleteRoleById(Long id);
    Boolean setRoleToUserByUserIdAndRoleId(Long userId, Long roleId);
    Boolean deleteRoleFromUserByUserIdAndRoleId(Long userId, Long roleId);
}
