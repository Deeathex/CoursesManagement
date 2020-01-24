package com.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDTO implements Serializable {
    private String name;
    private String surname;
    private String password;
    private String newPassword;
    private String rewrittenPassword;
}
