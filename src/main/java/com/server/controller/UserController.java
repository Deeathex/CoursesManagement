package com.server.controller;

import com.server.dto.AccountDTO;
import com.server.dto.UserDTO;
import com.server.dto.mapper.UserMapper;
import com.server.model.User;
import com.server.model.enums.Role;
import com.server.service.UserService;
import com.server.utils.Utils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping(value = "users-management")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class.getName());

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.userMapper = new UserMapper();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        User user = userMapper.userDTOToUser(userDTO);
        boolean registered = userService.register(user);

        if (!registered) {
            return new ResponseEntity<>(Utils.getErrorMessage("Email already registered or incorrect"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userMapper.userToUserDTO(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        if (!userService.valid(email, password)) {
            return new ResponseEntity<>(Utils.getErrorMessage("Incorrect email or password"), HttpStatus.UNAUTHORIZED);
        }

        session.setAttribute(Utils.EMAIL_SESSION_ATTRIBUTE, email);
        String json = userMapper.getJsonMessageAsString(session, userMapper.userToUserDTO(userService.getByEmail(email)));
        LOG.debug("Sending json to frontend: {} ", json);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.removeAttribute("email");
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(HttpSession session) {
        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userMapper.usersToUsersDTO(userService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/users/{role}")
    public ResponseEntity<?> getAllUsersByRole(@PathVariable("role") Role role, HttpSession session) {
        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(userMapper.usersToUsersDTO(userService.getAllBy(role)), HttpStatus.OK);
    }

    @PutMapping(value = "/save-account-settings/")
    public ResponseEntity<?> saveAccountSettings(
            @ModelAttribute AccountDTO accountDTO,
            @RequestParam("file") MultipartFile file,
            HttpSession session) {

        if (!Utils.isValid(session)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        User user = userService.getUserFromSession(session);

        if (checkAccountDTO(accountDTO)
                && (!userService.valid(user.getEmail(), accountDTO.getPassword())
                || !accountDTO.getNewPassword().equals(accountDTO.getRewrittenPassword()))) {
            LOG.info("Passwords do not match.");
            return new ResponseEntity<>(Utils.getErrorMessage("Passwords do not match."), HttpStatus.BAD_REQUEST);
        }

        User newUser = userMapper.accountDTOToUser(accountDTO);
        newUser.setEmail(user.getEmail());
        try {
            newUser.setPicture(file.getBytes());
        } catch (IOException e) {
            LOG.info("Could not process avatar.");
            e.printStackTrace();
        }

        userService.saveAccountSettings(newUser);

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
