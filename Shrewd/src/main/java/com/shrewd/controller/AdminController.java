package com.shrewd.controller;

import com.shrewd.model.users.Employee;
import com.shrewd.service.implemantation.AdminServicesImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminServicesImpl adminServicesImpl;

    public AdminController(AdminServicesImpl adminServicesImpl) {
        this.adminServicesImpl = adminServicesImpl;
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<Employee>> getAllUsers() {
        List<Employee> users = adminServicesImpl.getAllUsers();
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
