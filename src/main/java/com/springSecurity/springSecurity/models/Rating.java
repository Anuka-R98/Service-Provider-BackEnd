package com.springSecurity.springSecurity.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "ratings")
public class Rating {
    @Id
    private String rating_id;

    @DBRef
    private Set<User> rating_user_nam = new HashSet<>();

    @NotBlank
    private double rating_value;
}
