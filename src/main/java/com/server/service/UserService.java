package com.server.service;

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
import java.util.Objects;

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

    /***
     * Returns a user from DB by his email.
     * @param email the email to be searching for
     * @return the User that was registered with the given mail
     */
    public User getByEmail(String email) {
        return repository.findByEmail(email).orElse(null);
    }

    /***
     * Performs the registration on the given user.
     * @param user to be registered
     * @return true if the user was successfully registered or false otherwise
     */
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

    /***
     * Returns all users with the specified role.
     * @param role is one of PROFESSOR, STUDENT, NOT_SPECIFIED
     * @return a list with the users from db that have the specified role
     */
    public List<User> getAllBy(Role role) {
        return (List<User>) repository.findAllByRole(role).orElse(new ArrayList<>());
    }

    /***
     * This method will save the account settings for a given user.
     * @param newUser is the entity for which to be looking for in DB
     */
    public User saveAccountSettings(User newUser) {
        User userDB = repository.findByEmail(newUser.getEmail()).orElse(null);
        return repository.save(Objects.requireNonNull(Utils.updateUser(userDB, newUser)));
    }

    /***
     * Checks the current email and password to see if the credentials were already registered.
     * Checks for them not be valid.
     * @param email the supplied email
     * @param password the supplied password
     * @return false if email is already registered and the supplied password correspond to the one from DB,
     * true otherwise
     */
    public boolean isNotValid(String email, String password) {
        User userDB = getByEmail(email);
        return userDB == null || !password.equalsIgnoreCase(userDB.getPassword());
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
