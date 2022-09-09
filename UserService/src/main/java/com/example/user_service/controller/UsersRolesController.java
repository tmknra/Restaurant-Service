package com.example.user_service.controller;

import com.example.user_service.dto.in.RoleInDto;
import com.example.user_service.entity.RoleEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/roles")
public interface UsersRolesController {

    @PostMapping("/new")
    RoleEntity createNewRole(@RequestBody RoleInDto role);

    @DeleteMapping("/delete/{id}")
    boolean deleteRoleById(@PathVariable Long id);

    @PostMapping("/set/{roleId}/{userId}")
    boolean setRoleToUserByUserIdAndRoleId(@PathVariable Long userId,@PathVariable Long roleId);

    @DeleteMapping("/user/{userId}/delete/{roleId}")
    boolean deleteRoleFromUserByUserIdAndRoleId(@PathVariable Long userId,@PathVariable Long roleId);

}
