package ru.inno.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest(User user){
        this.username = user.getUserLogin();
        this.password = user.getUserPassword();
    }
}
