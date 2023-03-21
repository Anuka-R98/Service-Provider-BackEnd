package com.ServiceProvider.App.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ratings")
public class Rating {
    @Id
    private String id;
    @NotBlank
    private double rating;
    @NotBlank
    private String comment;
    String userId;
    String userName;
    String serviceId;
    String serviceName;

}
