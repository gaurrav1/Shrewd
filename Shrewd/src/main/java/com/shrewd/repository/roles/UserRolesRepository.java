package com.shrewd.repository.roles;

import com.shrewd.model.roles.UserRole;
import com.shrewd.model.roles.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByRoleName(UserRoles userRoles);
}
