package com.ServiceProvider.App.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "services")
public class Service {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String service_name;

    @NotBlank
    @Size(max = 500)
    @Email
    private String description;

}
