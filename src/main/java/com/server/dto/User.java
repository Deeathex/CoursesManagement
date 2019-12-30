package com.server.dto;

import com.server.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class User {
    private String name;
    private String surname;
    private String email;
    private byte[] picture;
    private Set<Role> roles;
}
