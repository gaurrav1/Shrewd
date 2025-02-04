package com.shrewd.service.implemantation;

import com.shrewd.model.users.Employee;
import com.shrewd.model.users.ROLES;
import com.shrewd.model.users.Role;
import com.shrewd.repository.users.EmployeeRepository;
import com.shrewd.repository.users.roles.RolesRepository;
import com.shrewd.security.communication.request.RegisterRequest;
import com.shrewd.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(RegisterRequest registerRequest) {
        try{
            if (employeeRepository.existsByEmail(registerRequest.getEmail())) {
                return ResponseEntity.badRequest().body("Error: Email is already in use.");
            }
            if (employeeRepository.existsByUsername(registerRequest.getUsername())) {
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

            Employee employee = new Employee(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    passwordEncoder.encode(registerRequest.getPassword())
            );

            employee.setName(registerRequest.getName());
            employee.setPhone(registerRequest.getPhone());
            employee.setAccountNonLocked(true);
            employee.setAccountNonExpired(true);
            employee.setCredentialsNonExpired(true);
            employee.setEnabled(true);
            employee.setCredentialsExpiryDate(LocalDate.now().plusYears(1));
            employee.setAccountExpiryDate(LocalDate.now().plusYears(1));
            employee.setRole(role);

            employeeRepository.save(employee);

            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

}
