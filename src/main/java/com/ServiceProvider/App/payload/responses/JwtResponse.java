package com.ServiceProvider.App.payload.responses;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private List<String> roles;
    private String phoneNo;

    public JwtResponse(String token, String id, String username, String email, String phoneNo, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.phoneNo = phoneNo;
    }

}
