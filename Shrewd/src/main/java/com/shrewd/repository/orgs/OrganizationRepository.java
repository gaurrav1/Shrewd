package com.shrewd.repository.orgs;

import com.shrewd.model.orgs.Organization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {
    Optional<Organization> findByUsername(String username);
    Optional<Organization> findByEmail(String email);

    boolean existsByEmail(String mail);

    boolean existsByUsername(@NotBlank @Size(min = 3, max = 20) String username);

    Organization findByTenantId(String tenantId);
}
