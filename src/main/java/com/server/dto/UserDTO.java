package com.server.dto;

import com.server.model.enums.Role;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private byte[] picture;
    private Role role;
}
