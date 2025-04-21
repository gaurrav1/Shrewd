package com.shrewd.service.users;

import com.shrewd.model.users.model.Users;
import com.shrewd.model.users.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServices {
    private final UsersRepository usersRepository;

    public AdminServices(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public void updateUserRole() {
    }

    public void getUser() {
    }
}
