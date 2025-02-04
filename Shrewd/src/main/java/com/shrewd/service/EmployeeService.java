package com.shrewd.service;

import com.shrewd.security.communication.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface EmployeeService {
    ResponseEntity<?> register(RegisterRequest registerRequest);
}
