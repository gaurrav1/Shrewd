package com.shrewd.service.users;

import com.shrewd.config.TenantContext;
import com.shrewd.dtos.UserDTO;
import com.shrewd.model.users.Users;
import com.shrewd.model.users.ROLES;
import com.shrewd.model.users.Role;
import com.shrewd.repository.users.UsersRepository;
import com.shrewd.repository.users.roles.RolesRepository;
import com.shrewd.security.communication.request.LoginRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import com.shrewd.security.communication.response.LoginResponse;
import com.shrewd.security.jwt.JwtUtils;
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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public UsersService(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        try{
            if (usersRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Error: Email is already in use.");
            }
            if (usersRepository.existsByUsername(registerRequest.getUsername())) {
                return ResponseEntity.badRequest().body("Error: Username is already in use.");
            }

            Set<String> strRoles = registerRequest.getRole();
            if (strRoles.size() != 1) {
                return ResponseEntity.badRequest().body("Error: Exactly one role must be specified.");
            }

            String roleStr = strRoles.iterator().next().toUpperCase();
            Role role;

            switch (roleStr) {
                case "EMPLOYEE":
                    role = rolesRepository.findByRoleName(ROLES.EMPLOYEE)
                            .orElseThrow(() -> new RuntimeException("Error: EMPLOYEE role not found."));
                    break;
                case "MANAGER":
                    role = rolesRepository.findByRoleName(ROLES.MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: MANAGER role not found."));
                    break;
                case "HR":
                    role = rolesRepository.findByRoleName(ROLES.HR)
                            .orElseThrow(() -> new RuntimeException("Error: HR role not found."));
                    break;
                case "ADMIN":
                    role = rolesRepository.findByRoleName(ROLES.ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: ADMIN role not found."));
                    break;
                default:
                    return ResponseEntity.badRequest().body("Error: Invalid role specified.");
            }

            Users users = new Users(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword())
            );

            users.setName(registerRequest.getName());
            users.setPhone(registerRequest.getPhone());
            users.setAccountNonLocked(true);
            users.setAccountNonExpired(true);
            users.setCredentialsNonExpired(true);
            users.setEnabled(true);
            users.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
            users.setAccountExpiryDate(LocalDate.now().plusYears(1));
            users.setRole(role);

            usersRepository.save(users);

            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String tenantId = TenantContext.getCurrentTenant();

        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails, tenantId);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken, tenantId);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not authenticated.");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        try {
            Optional<Users> user = usersRepository.findByEmail(userDetails.getUsername());
            UserDTO userDTO = getUserDTO(user);
            if (userDTO == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not found.");
            }
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not found.");
        }
    }

    private static UserDTO getUserDTO(Optional<Users> user) {
        if (user.isEmpty()) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.get().getUsername());
        userDTO.setEmail(user.get().getEmail());
        userDTO.setName(user.get().getName());
        userDTO.setPhone(user.get().getPhone());
        userDTO.setRole(user.get().getRole());
        userDTO.setAccountNonLocked(user.get().isAccountNonLocked());
        userDTO.setAccountNonExpired(user.get().isAccountNonExpired());
        userDTO.setCredentialsNonExpired(user.get().isCredentialsNonExpired());
        userDTO.setEnabled(user.get().isEnabled());
        userDTO.setCredentialsExpiryDate(user.get().getCredentialsExpiryDate());
        userDTO.setAccountExpiryDate(user.get().getAccountExpiryDate());
        userDTO.setCreatedDate(user.get().getCreatedDate());
        userDTO.setUpdatedDate(user.get().getUpdatedDate());
        return userDTO;
    }
}
