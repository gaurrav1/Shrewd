package com.shrewd.repository.users.roles;

import com.shrewd.model.users.Role;
import com.shrewd.model.users.ROLES;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ROLES ROLES);
}
