package com.shrewd.repository.roles;

import com.shrewd.model.roles.OrgRoles;
import com.shrewd.model.roles.OrgRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrganizationRolesRepository extends JpaRepository<OrgRole, Long> {
    Optional<OrgRole> findByRoleName(OrgRoles orgRoles);
}
