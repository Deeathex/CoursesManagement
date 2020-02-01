package com.server.dto.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.server.dto.AccountDTO;
import com.server.dto.UserDTO;
import com.server.model.User;
import lombok.NoArgsConstructor;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
@NoArgsConstructor
public class UserMapper {

    public static User userDTOToUser(UserDTO userDTO) {
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

    public static UserDTO userToUserDTO(User user) {
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

    public static User accountDTOToUser(AccountDTO accountDTO) {
        User user = new User();

        user.setName(accountDTO.getName());
        user.setSurname(accountDTO.getSurname());
        user.setPassword(accountDTO.getNewPassword());

        return user;
    }

    public static List<UserDTO> usersToUsersDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            usersDTO.add(userToUserDTO(user));
        }

        return usersDTO;
    }

    public static List<User> usersDTOToUsers(List<UserDTO> usersDTO) {
        List<User> users = new ArrayList<>();

        for (UserDTO userDTO : usersDTO) {
            users.add(userDTOToUser(userDTO));
        }

        return users;
    }

    public static String getJsonMessageAsString(HttpSession session, UserDTO userDTO) {
        JsonNodeFactory factory = new JsonNodeFactory(false);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode login = factory.objectNode();
        login.put("session_id", session.getId());

        JsonNode node = mapper.valueToTree(userDTO);
        login.set("user", node);

        return login.toString();
    }
}
