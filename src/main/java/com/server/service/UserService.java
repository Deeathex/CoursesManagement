package com.server.service;

import com.server.model.User;
import com.server.model.enums.Role;
import com.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public Role getRoleByEmail(String email) {
        final String emailPattern = "(.+)@(s*cs.ubbcluj.ro)";
        final String emailProfessors = "@scs.ubbcluj.ro";
        final String emailStudents = "@cs.ubbcluj.ro";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        if (matcher.matches()) {
            if (matcher.group(1).equalsIgnoreCase(emailProfessors)) {
                return Role.PROFESSOR;
            }
            if (matcher.group(1).equalsIgnoreCase(emailStudents)) {
                return Role.STUDENT;
            }
        }

        return Role.NOT_SUPPORTED;
    }
}
