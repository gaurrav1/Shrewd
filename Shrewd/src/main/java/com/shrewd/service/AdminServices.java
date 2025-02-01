package com.shrewd.service;

import com.shrewd.model.Employee;
import com.shrewd.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {
    private final EmployeeRepository employeeRepository;

    public AdminServices(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllUsers() {
        return employeeRepository.findAll();
    }

    public void updateUserRole() {
    }

    public void getUser() {
    }
}
