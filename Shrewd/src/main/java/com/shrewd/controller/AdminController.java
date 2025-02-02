package com.shrewd.controller;

import com.shrewd.model.Employee;
import com.shrewd.service.AdminServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminServices adminServices;

    public AdminController(AdminServices adminServices) {
        this.adminServices = adminServices;
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<Employee>> getAllUsers() {
        List<Employee> users = adminServices.getAllUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

//    @PutMapping("/update-role")
//    public ResponseEntity<String> updateUserRole() {
//        return null;
//    }
//
//    @GetMapping("/user/{id}")
//    public ResponseEntity<UserDTO> getUser() {
//        return null;
//    }

}
