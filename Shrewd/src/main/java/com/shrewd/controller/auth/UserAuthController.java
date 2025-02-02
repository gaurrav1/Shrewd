//package com.shrewd.controller.auth;
//
//import com.shrewd.model.Employee;
//import com.shrewd.model.Organization;
//import com.shrewd.model.ROLES;
//import com.shrewd.model.Role;
//import com.shrewd.repository.EmployeeRepository;
//import com.shrewd.repository.OrganizationRepository;
//import com.shrewd.repository.roles.RolesRepository;
//import com.shrewd.security.communication.request.LoginRequest;
//import com.shrewd.security.communication.request.RegisterRequest;
//import com.shrewd.security.communication.response.LoginResponse;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/user/auth")
//public class UserAuthController {
//
//    private final EmployeeRepository employeeRepository;
//    private final PasswordEncoder passwordEncoder;
//    private final RolesRepository rolesRepository;
//    private final AuthenticationManager authenticationManager;
//    private final OrganizationRepository organizationRepository;
//
//    public UserAuthController(
//            EmployeeRepository employeeRepository,
//            PasswordEncoder passwordEncoder,
//            RolesRepository rolesRepository,
//            AuthenticationManager authenticationManager, OrganizationRepository organizationRepository) {
//        this.employeeRepository = employeeRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.rolesRepository = rolesRepository;
//        this.authenticationManager = authenticationManager;
//        this.organizationRepository = organizationRepository;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
//        try{
//            if (employeeRepository.existsByEmail(registerRequest.getEmail())) {
//                return ResponseEntity.badRequest().body("Error: Email is already in use.");
//            }
//            if (employeeRepository.existsByUsername(registerRequest.getUsername())) {
//                return ResponseEntity.badRequest().body("Error: Username is already in use.");
//            }
//
//            // Ensure exactly one role is provided
//            Set<String> strRoles = registerRequest.getRole();
//            if (strRoles.size() != 1) {
//                return ResponseEntity.badRequest().body("Error: Exactly one role must be specified.");
//            }
//
//            // Get the single role
//            String roleStr = strRoles.iterator().next().toUpperCase();
//            Role role;
//
//            switch (roleStr) {
//                case "EMPLOYEE":
//                    role = rolesRepository.findByRoleName(ROLES.EMPLOYEE)
//                            .orElseThrow(() -> new RuntimeException("Error: EMPLOYEE role not found."));
//                    break;
//                case "MANAGER":
//                    role = rolesRepository.findByRoleName(ROLES.MANAGER)
//                            .orElseThrow(() -> new RuntimeException("Error: MANAGER role not found."));
//                    break;
//                case "HR":
//                    role = rolesRepository.findByRoleName(ROLES.HR)
//                            .orElseThrow(() -> new RuntimeException("Error: HR role not found."));
//                    break;
//                case "ADMIN":
//                    role = rolesRepository.findByRoleName(ROLES.ADMIN)
//                            .orElseThrow(() -> new RuntimeException("Error: ADMIN role not found."));
//                    break;
//                default:
//                    return ResponseEntity.badRequest().body("Error: Invalid role specified.");
//            }
//
//            // Create new employee
//            Employee employee = new Employee(
//                    registerRequest.getUsername(),
//                    registerRequest.getEmail(),
//                    passwordEncoder.encode(registerRequest.getPassword())
//            );
//
//            employee.setAccountNonLocked(true);
//            employee.setAccountNonExpired(true);
//            employee.setCredentialsNonExpired(true);
//            employee.setEnabled(true);
//            employee.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
//            employee.setAccountExpiryDate(LocalDate.now().plusYears(1));
//            employee.setRole(role); // Assign single role
//
//            String org_username = registerRequest.getOrganization();
//            Organization organization = organizationRepository.findByUsername(org_username)
//                    .orElseThrow(() -> new RuntimeException("Error: Organization not found."));
//
//            employee.setOrganization(organization);
//            employeeRepository.save(employee);
//
//            return ResponseEntity.ok("User registered successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
//            );
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            List<String> roles = userDetails.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.toList());
//
//            LoginResponse response = new LoginResponse(userDetails.getUsername(), roles);
//
//            return ResponseEntity.ok(response);
//
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: Invalid email or password.");
//        }
//    }
//
//    @GetMapping("/user")
//    public ResponseEntity<?> getUserDetails() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error: User not authenticated.");
//        }
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        return ResponseEntity.ok(userDetails);
//    }
//}
