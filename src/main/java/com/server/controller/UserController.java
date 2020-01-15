package com.server.controller;

import com.server.dto.UserDTO;
import com.server.dto.mapper.UserMapper;
import com.server.model.User;
import com.server.model.enums.Role;
import com.server.service.UserService;
import com.server.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "users-management")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class.getName());

    private final UserService userService;
    private UserMapper userMapper;

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
            JSONObject json = new JSONObject().put("message", "Email already registered or incorrect");
            return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        User userDB = userService.getByEmail(email);

        if (userDB == null || !password.equalsIgnoreCase(userDB.getPassword())) {
            String json = new JSONObject().put("message", "Incorrect email or password").toString();
            return new ResponseEntity<>(json, HttpStatus.UNAUTHORIZED);
        }

        session.setAttribute(Utils.SESSION_ATTRIBUTE, email);
        String json = userMapper.getJsonMessageAsString(session, userDB);
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
        if (Utils.isValid(session)) {
            return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/users/{role}")
    public ResponseEntity<?> getAllUsersByRole(@PathVariable("role") Role role, HttpSession session) {
        if (Utils.isValid(session)) {
            return new ResponseEntity<>(userService.getAllBy(role), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
