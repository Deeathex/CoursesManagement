package com.server.dto.mapper;

import com.server.dto.AccountDTO;
import com.server.dto.UserDTO;
import com.server.model.User;
import lombok.NoArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
@NoArgsConstructor
public class UserMapper {

    public User userDTOToUser(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
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

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setSurname(user.getSurname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        userDTO.setPicture(user.getPicture());

        return userDTO;
    }

    public User accountDTOToUser(AccountDTO accountDTO) {
        User user = new User();

        user.setName(accountDTO.getName());
        user.setSurname(accountDTO.getSurname());
        user.setPassword(accountDTO.getNewPassword());

        return user;
    }

    public List<UserDTO> usersToUsersDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            usersDTO.add(userToUserDTO(user));
        }

        return usersDTO;
    }

    public List<User> usersDTOToUsers(List<UserDTO> usersDTO) {
        List<User> users = new ArrayList<>();

        for (UserDTO userDTO : usersDTO) {
            users.add(userDTOToUser(userDTO));
        }

        return users;
    }

    public String getJsonMessageAsString(HttpSession session, UserDTO userDTO) {
        return new JSONObject()
                .put("session_id", session.getId())
                .put("user", userDTO)
                .toString();
    }
}
