package com.ServiceProvider.App.models;

import jakarta.validation.constraints.NotBlank;
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
    private String id;
    @NotBlank
    private int rating;
    private String comment;
    @DBRef
    private User user;
    @DBRef
    private MService service;

}
