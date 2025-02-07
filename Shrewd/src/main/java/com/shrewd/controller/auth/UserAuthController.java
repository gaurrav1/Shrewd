package com.shrewd.controller.auth;

import com.shrewd.security.communication.request.LoginRequest;
import com.shrewd.security.communication.request.RegisterRequest;
import com.shrewd.service.users.UsersService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/user")
public class UserAuthController {

    private final UsersService usersService;

    public UserAuthController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return usersService.register(registerRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return usersService.login(loginRequest);
    }

}
