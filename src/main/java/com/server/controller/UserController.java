package com.server.controller;

import com.server.dto.AccountDTO;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> login() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String password = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        if (userService.isNotValid(email, password)) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect email or password"), HttpStatus.UNAUTHORIZED);
        }

        LOG.info("User {} logged in.", email);
        return new ResponseEntity<>(UserMapper.userToUserDTO(userService.getByEmail(email)), HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(UserMapper.usersToUsersDTO(userService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/users/{role}")
    public ResponseEntity<?> getAllUsersByRole(@PathVariable("role") Role role) {
        return new ResponseEntity<>(UserMapper.usersToUsersDTO(userService.getAllBy(role)), HttpStatus.OK);
    }

    @PutMapping(value = "/account-settings/")
    public ResponseEntity<?> saveAccountSettings(
            @ModelAttribute AccountDTO accountDTO,
            @RequestParam("file") MultipartFile file) {

        if (checkAccountDTO(accountDTO)
                && (userService.isNotValid(accountDTO.getEmail(), accountDTO.getPassword())
                || !accountDTO.getNewPassword().equals(accountDTO.getRewrittenPassword()))) {
            LOG.info("Passwords do not match.");
            return new ResponseEntity<>(Utils.getErrorMessage("Passwords do not match."), HttpStatus.BAD_REQUEST);
        }

        User newUser = UserMapper.accountDTOToUser(accountDTO);
        newUser.setEmail(accountDTO.getEmail());
        try {
            newUser.setPicture(file.getBytes());
        } catch (IOException e) {
            LOG.info("Could not process avatar.");
            e.printStackTrace();
        }

        userService.saveAccountSettings(newUser);

        LOG.info("User {} saves changes in account settings.", accountDTO.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/save-account-settings/")
    public ResponseEntity<?> saveAccountSettings(@RequestBody AccountDTO accountDTO) {

        if (checkAccountDTO(accountDTO)
                && (userService.isNotValid(accountDTO.getEmail(), accountDTO.getPassword())
                || !accountDTO.getNewPassword().equals(accountDTO.getRewrittenPassword()))) {
            LOG.info("Passwords do not match.");
            return new ResponseEntity<>(Utils.getErrorMessage("Passwords do not match."), HttpStatus.BAD_REQUEST);
        }

        User newUser = UserMapper.accountDTOToUser(accountDTO);
        newUser.setEmail(accountDTO.getEmail());

        userService.saveAccountSettings(newUser);

        LOG.info("User {} saves changes in account settings.", accountDTO.getEmail());
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
