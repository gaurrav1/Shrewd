package com.shrewd.service.implemantation;

import com.shrewd.model.users.Employee;
import com.shrewd.repository.users.EmployeeRepository;
import com.shrewd.service.AdminServices;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServicesImpl implements AdminServices {
    private final EmployeeRepository employeeRepository;

    public AdminServicesImpl(EmployeeRepository employeeRepository) {
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
