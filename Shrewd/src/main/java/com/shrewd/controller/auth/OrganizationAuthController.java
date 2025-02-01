package com.shrewd.controller.auth;

import com.shrewd.model.Organization;
import com.shrewd.model.roles.ROLES;
import com.shrewd.model.roles.Role;
import com.shrewd.repository.OrganizationRepository;
import com.shrewd.repository.roles.RolesRepository;
import com.shrewd.security.communication.request.LoginRequest;
import com.shrewd.security.communication.request.OrgRegisterRequest;
import com.shrewd.security.communication.response.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/organization/auth")
public class OrganizationAuthController {

    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final AuthenticationManager authenticationManager;

    public OrganizationAuthController(
            OrganizationRepository organizationRepository,
            PasswordEncoder passwordEncoder,
            RolesRepository rolesRepository,
            AuthenticationManager authenticationManager) {
        this.organizationRepository = organizationRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody OrgRegisterRequest registerRequest) {
        if (organizationRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use.");
        }
        if (organizationRepository.existsByUsername(registerRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already in use.");
        }

        Set<String> strRoles = registerRequest.getRole();
        if (strRoles.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Role is required.");
        }

        Optional<Role> roleOpt = rolesRepository.findByRoleName(ROLES.ORGANIZATION);

        if (roleOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Role is not found.");
        }

        Organization organization = new Organization(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())
        );

        organization.setAccountNonLocked(true);
        organization.setAccountNonExpired(true);
        organization.setCredentialsNonExpired(true);
        organization.setEnabled(true);
        organization.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
        organization.setAccountExpiryDate(LocalDate.now().plusYears(1));
        organization.setRole(roleOpt.get());

        organizationRepository.save(organization);

        return ResponseEntity.ok("Organization registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            LoginResponse response = new LoginResponse(userDetails.getUsername(), roles);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid email or password.");
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not authenticated.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails);
    }
}
