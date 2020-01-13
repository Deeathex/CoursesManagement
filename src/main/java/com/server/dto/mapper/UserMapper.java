package com.server.dto.mapper;

import com.server.dto.UserDTO;
import com.server.model.User;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@NoArgsConstructor
public class UserMapper {

    public User userDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        return user;
    }

    public UserDTO userToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    public JSONObject getJsonMessage(HttpSession session, User user) {
        return new JSONObject()
                .put("session_id", session.getId())
                .put("user", user);
    }
}
