package com.ServiceProvider.App.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @NotBlank
    @Size(max = 10)
    private String phoneNo;

    @DBRef
    private Set<Role> roles = new HashSet<>();
//    @DBRef
//    private List<Rating> ratings;

    public User() {
    }
    public User(String username, String email, String password, Set<Role> roles, String phoneNo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.phoneNo = phoneNo;
    }
    public User(String username, String email, String password, String phoneNo) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

}
