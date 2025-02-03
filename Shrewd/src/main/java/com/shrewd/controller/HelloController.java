package com.shrewd.controller;

import com.shrewd.model.users.Employee;
import com.shrewd.repository.users.EmployeeRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private final EmployeeRepository employeeRepository;

    public HelloController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/hello")
    public List<String> getEmployeeNames() {
        // Fetch all employees from the repository
        List<Employee> employees = employeeRepository.findAll();

        // Extract the names of the employees
        return employees.stream()
                .map(Employee::getEmail) // Assuming Employee entity has a getName() method
                .collect(Collectors.toList());
    }

}
