package com.shrewd.repository.roles;

import com.shrewd.model.roles.Role;
import com.shrewd.model.roles.ROLES;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ROLES ROLES);
}
