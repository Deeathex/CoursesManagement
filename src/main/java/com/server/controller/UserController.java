package com.server.controller;

import com.server.dto.AccountDTO;
import com.server.dto.CredentialsDTO;
import com.server.dto.UserDTO;
import com.server.dto.mapper.UserMapper;
import com.server.model.User;
import com.server.model.enums.Role;
import com.server.service.UserService;
import com.server.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping(value = "users-management")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class.getName());

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        User user = UserMapper.userDTOToUser(userDTO);
        boolean registered = userService.register(user);

        if (!registered) {
            return new ResponseEntity<>(Utils.getErrorMessage("Email already registered or incorrect"), HttpStatus.BAD_REQUEST);
        }

        LOG.info("A user just registered to the platform.");
        return new ResponseEntity<>(UserMapper.userToUserDTO(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody CredentialsDTO credentialsDTO,
            HttpSession session) {

        String email = credentialsDTO.getEmail();
        String password = credentialsDTO.getPassword();

        if (userService.isNotValid(email, password)) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect email or password"), HttpStatus.UNAUTHORIZED);
        }
        session.setAttribute(Utils.EMAIL_SESSION_ATTRIBUTE, email);

        String json = UserMapper.getJsonMessageAsString(session, UserMapper.userToUserDTO(userService.getByEmail(email)));

        LOG.info("User {} logged in.", email);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        String email = (String) session.getAttribute(Utils.EMAIL_SESSION_ATTRIBUTE);
        session.removeAttribute(Utils.EMAIL_SESSION_ATTRIBUTE);
        session.invalidate();

        LOG.info("User {} logged out.", email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        if (Utils.isNotValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(UserMapper.usersToUsersDTO(userService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/users/{role}")
    public ResponseEntity<?> getAllUsersByRole(@PathVariable("role") Role role, HttpSession session) {
        if (Utils.isNotValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(UserMapper.usersToUsersDTO(userService.getAllBy(role)), HttpStatus.OK);
    }

    @PutMapping(value = "/save-account-settings/")
    public ResponseEntity<?> saveAccountSettings(
            @ModelAttribute AccountDTO accountDTO,
            @RequestParam("file") MultipartFile file,
            HttpSession session) {

        if (Utils.isNotValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        if (checkAccountDTO(accountDTO)
                && (userService.isNotValid(user.getEmail(), accountDTO.getPassword())
                || !accountDTO.getNewPassword().equals(accountDTO.getRewrittenPassword()))) {
            LOG.info("Passwords do not match.");
            return new ResponseEntity<>(Utils.getErrorMessage("Passwords do not match."), HttpStatus.BAD_REQUEST);
        }

        User newUser = UserMapper.accountDTOToUser(accountDTO);
        newUser.setEmail(user.getEmail());
        try {
            newUser.setPicture(file.getBytes());
        } catch (IOException e) {
            LOG.info("Could not process avatar.");
            e.printStackTrace();
        }

        userService.saveAccountSettings(newUser);

        LOG.info("User {} saves changes in account settings.", user.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean checkAccountDTO(AccountDTO accountDTO) {
        if (accountDTO.getPassword() == null) {
            return false;
        }

        if (accountDTO.getNewPassword() == null) {
            return false;
        }

        return accountDTO.getRewrittenPassword() != null;
    }
}
