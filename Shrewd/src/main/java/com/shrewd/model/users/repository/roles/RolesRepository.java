package com.shrewd.model.users.repository.roles;

import com.shrewd.model.users.model.Role;
import com.shrewd.model.users.model.ROLES;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolesRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(ROLES ROLES);
}
