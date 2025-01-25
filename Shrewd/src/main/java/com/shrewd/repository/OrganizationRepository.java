package com.shrewd.repository;

import com.shrewd.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, String> {
    Optional<Organization> findByUsername(String username);
    Optional<Organization> findByEmail(String email);

    boolean existsByEmail(String mail);
}
