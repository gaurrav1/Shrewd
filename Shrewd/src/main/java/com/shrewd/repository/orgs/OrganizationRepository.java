package com.shrewd.repository.orgs;

import com.shrewd.model.orgs.Organization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {
    Optional<Organization> findByEmail(String email);
    boolean existsByEmail(String mail);

    boolean existsByTenantId(String tenantId);
}
