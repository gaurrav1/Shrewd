package com.shrewd.repository.users;

import com.shrewd.model.users.Employee;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String mail);

    boolean existsByUsername(@NotBlank @Size(min = 3, max = 20) String username);
}
