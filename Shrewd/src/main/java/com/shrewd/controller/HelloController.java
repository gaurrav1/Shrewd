package com.shrewd.controller;

import com.shrewd.model.users.Users;
import com.shrewd.repository.users.UsersRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private final UsersRepository usersRepository;

    public HelloController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("/hello")
    public List<String> getEmployeeNames() {
        // Fetch all employees from the repository
        List<Users> users = usersRepository.findAll();

        // Extract the names of the employees
        return users.stream()
                .map(Users::getEmail) // Assuming Employee entity has a getName() method
                .collect(Collectors.toList());
    }

}
