package com.ServiceProvider.App.payload.requests;

import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {

    private String id;
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
    private String phoneNo;

}
