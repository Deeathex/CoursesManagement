package com.server.service;

import com.server.model.User;
import com.server.model.enums.Role;
import com.server.repository.UserRepository;
import com.server.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public User getByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    public boolean register(User user) {
        if (user == null || getByEmail(user.getEmail()) != null) {
            return false;
        }

        Role role = Utils.getRoleByEmail(user.getEmail());
        if (role == Role.NOT_SUPPORTED) {
            return false;
        }

        user.setRole(role);
        repository.save(user);
        return true;
    }

    public List<User> getAllBy(Role role) {
        return (List<User>) repository.findAllByRole(role).orElse(new ArrayList<>());
    }
}
