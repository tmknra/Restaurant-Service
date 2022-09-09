package com.example.user_service.controller.impl;

import com.example.user_service.controller.UsersRolesController;
import com.example.user_service.dto.in.RoleInDto;
import com.example.user_service.dto.out.RoleOutDto;
import com.example.user_service.entity.RoleEntity;
import com.example.user_service.mapper.RoleMapper;
import com.example.user_service.service.UsersRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersRolesControllerImpl implements UsersRolesController {

    @Autowired
    private UsersRolesService usersRolesService;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleEntity createNewRole(RoleInDto role) {
        return usersRolesService.createNewRole(role);
    }

    @Override
    public boolean deleteRoleById(Long id) {
        return usersRolesService.deleteRoleById(id);
    }

    @Override
    public boolean setRoleToUserByUserIdAndRoleId(Long userId, Long roleId) {
        return usersRolesService.setRoleToUserByUserIdAndRoleId(userId, roleId);
    }

    @Override
    public boolean deleteRoleFromUserByUserIdAndRoleId(Long userId, Long roleId) {
        return usersRolesService.deleteRoleFromUserByUserIdAndRoleId(userId, roleId);
    }
}
