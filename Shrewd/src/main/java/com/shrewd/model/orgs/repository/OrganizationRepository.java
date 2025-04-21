package com.shrewd.model.orgs.repository;

import com.shrewd.model.orgs.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {
    Optional<Organization> findByEmail(String email);

    Optional<Organization> findByTenantIdAndStatus(String tenantId, String status);

    boolean existsByEmail(String mail);

    boolean existsByTenantId(String tenantId);
}
