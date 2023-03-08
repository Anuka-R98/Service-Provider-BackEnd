package com.ServiceProvider.App.payload.responses;

import com.ServiceProvider.App.models.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;

    public UserResponse() {
    }

    /* Used in sign up */
    public UserResponse(String username, String email, Set<Role> roles) {
    }
}
