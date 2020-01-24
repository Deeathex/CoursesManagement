package com.server.service;

import com.server.controller.UserController;
import com.server.model.User;
import com.server.model.enums.Role;
import com.server.repository.UserRepository;
import com.server.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = LogManager.getLogger(UserService.class.getName());

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

    /***
     * This method will save the account settings for a given user.
     * @param newUser is the entity for which to be looking for in DB
     */
    public User saveAccountSettings(User newUser) {
        User userDB = repository.findByEmail(newUser.getEmail()).orElse(null);
        return repository.save(Utils.updateUser(userDB, newUser));
    }

    /***
     * Validates the provided email and password such that the given email already exists in DB
     * and the provided password matches the one from DB
     * @param email the supplied email
     * @param password the supplied password
     * @return true if email is already registered and the supplied password correspond to the one from DB,
     * false otherwise
     */
    public boolean valid(String email, String password) {
        User userDB = getByEmail(email);
        return userDB != null && password.equalsIgnoreCase(userDB.getPassword());
    }

    /***
     * Gets the user based on the current session
     * @param session the provided session
     * @return the user that is currently logged in.
     */
    public User getUserFromSession(HttpSession session) {
        String email = (String) session.getAttribute(Utils.EMAIL_SESSION_ATTRIBUTE);
        LOG.info("Got email from session: {}", email);

        return repository.findByEmail(email).orElse(null);
    }
}
