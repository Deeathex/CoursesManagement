package com.server.dto;

import com.server.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private byte[] picture;
    private Role role;
}
