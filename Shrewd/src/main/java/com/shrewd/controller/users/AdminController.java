package com.shrewd.controller.users;

import com.shrewd.model.users.Users;
import com.shrewd.service.users.AdminServices;
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
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = adminServices.getAllUsers();
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
