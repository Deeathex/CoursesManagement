package com.server.controller;

import com.server.dto.UserDTO;
import com.server.dto.mapper.UserMapper;
import com.server.model.User;
import com.server.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value = "user")
public class UserController {
    private static final Logger LOG = LogManager.getLogger(UserController.class.getName());
    private static final String SESSION_ATTRIBUTE = "email";

    private final UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        this.userMapper = new UserMapper();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session) {

        User userDB = userService.getByEmail(email);

        if (userDB != null && password.equalsIgnoreCase(userDB.getPassword())) {
            session.setAttribute(SESSION_ATTRIBUTE, email);
            JSONObject json = userMapper.getJsonMessage(session, userDB);
            LOG.debug("Sending json to frontend: {} ", json);

            return new ResponseEntity<>(json, HttpStatus.OK);
        }

        JSONObject json = new JSONObject().put("message", "Incorrect email or password");
        return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO, HttpSession session) {
        if (userService.getByEmail(userDTO.getEmail()) != null) {
            JSONObject json = new JSONObject().put("message", "Email already registered");
            return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
        }

        User user = userMapper.userDTOToUser(userDTO);
        session.setAttribute(SESSION_ATTRIBUTE, user.getEmail());
        user.setRole(userService.getRoleByEmail(user.getEmail()));
        //persist in DB

        JSONObject json = userMapper.getJsonMessage(session, user);
        System.out.println(json);
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.removeAttribute("email");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUser() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }
}
