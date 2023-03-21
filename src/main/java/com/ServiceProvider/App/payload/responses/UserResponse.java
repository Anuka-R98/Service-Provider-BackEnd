package com.ServiceProvider.App.payload.responses;

import lombok.Data;

import java.util.Set;

@Data
public class UserResponse {

    private String id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
    private String phoneNo;

    public UserResponse() {
    }

}
