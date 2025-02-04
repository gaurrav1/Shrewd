package com.shrewd.controller.auth;

import com.shrewd.security.communication.request.LoginRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import com.shrewd.security.communication.response.LoginResponse;
import com.shrewd.service.implemantation.EmployeeServiceImpl;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/auth")
public class UserAuthController {

    private final AuthenticationManager authenticationManager;
    private final EmployeeServiceImpl employeeServiceImpl;

    public UserAuthController(AuthenticationManager authenticationManager, EmployeeServiceImpl employeeServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return employeeServiceImpl.register(registerRequest);
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
