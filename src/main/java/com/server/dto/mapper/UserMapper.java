package com.server.dto.mapper;

import com.server.dto.UserDTO;
import com.server.model.User;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@SuppressWarnings("Duplicates")
@NoArgsConstructor
public class UserMapper {

    public User userDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        user.setPicture(userDTO.getPicture());
        return user;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setPicture(user.getPicture());
        return userDTO;
    }

    public String getJsonMessageAsString(HttpSession session, User user) {
        return new JSONObject()
                .put("session_id", session.getId())
                .put("user", user)
                .toString();
    }
}
