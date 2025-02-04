package com.shrewd.service;

import com.shrewd.model.users.Employee;

import java.util.List;

public interface AdminServices {
    List<Employee> getAllUsers();
    void updateUserRole();
    void getUser();
}
