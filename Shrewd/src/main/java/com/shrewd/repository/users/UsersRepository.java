package com.shrewd.repository.users;

import com.shrewd.model.users.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String mail);

    boolean existsByUsername(@NotBlank @Size(min = 3, max = 20) String username);
}
