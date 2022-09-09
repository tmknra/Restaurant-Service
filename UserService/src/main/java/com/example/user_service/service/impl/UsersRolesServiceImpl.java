package com.example.user_service.service.impl;

import com.example.user_service.dto.in.RoleInDto;
import com.example.user_service.dto.in.UserRoleInDto;
import com.example.user_service.entity.RoleEntity;
import com.example.user_service.entity.UserRoleEntity;
import com.example.user_service.mapper.RoleMapper;
import com.example.user_service.mapper.UserRoleMapper;
import com.example.user_service.repository.RolesRepository;
import com.example.user_service.repository.UserRoleRepository;
import com.example.user_service.service.UsersRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersRolesServiceImpl implements UsersRolesService {

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public RoleEntity createNewRole(RoleInDto role) {
        return rolesRepository.save(roleMapper.roleInDtoToRole(role));
    }

    @Override
    public Boolean deleteRoleById(Long id) {
        Optional<RoleEntity> byId = rolesRepository.findById(id);
        if (byId.isEmpty())
            throw new IllegalArgumentException();
        rolesRepository.delete(byId.get());
        return true;
    }

    @Override
    public Boolean setRoleToUserByUserIdAndRoleId(Long userId, Long roleId) {
        UserRoleEntity build = UserRoleEntity.builder().userId(userId).roleId(roleId).build();
        UserRoleEntity save = userRoleRepository.save(build);
        return userRoleRepository.existsById(save.getId());
    }

    @Override
    public Boolean deleteRoleFromUserByUserIdAndRoleId(Long userId, Long roleId) {
        Optional<UserRoleEntity> byUserIdAndRoleId = userRoleRepository.findByUserIdAndRoleId(userId, roleId);
        if (byUserIdAndRoleId.isEmpty())
            throw new IllegalArgumentException();
        userRoleRepository.delete(byUserIdAndRoleId.get());
        return true;
    }
}
