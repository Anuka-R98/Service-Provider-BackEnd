package com.ServiceProvider.App.payload.responses;

import com.ServiceProvider.App.models.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private List<String> roles;
    private String phoneNo;

    /* used for sign in */
    public JwtResponse(String token, String id, String username, String email, String phoneNo, List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.phoneNo = phoneNo;
    }

    /* used for sign up */
    public JwtResponse(String id, String username, String email, String phoneNo) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phoneNo = phoneNo;
    }

}
