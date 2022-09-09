package com.example.user_service.repository;

import com.example.user_service.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, Long> {
}
