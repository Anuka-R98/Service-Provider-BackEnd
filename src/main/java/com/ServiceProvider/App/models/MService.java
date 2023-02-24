package com.ServiceProvider.App.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "services")
public class MService {
    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 500)
    @Email
    private String description;

//    public MService() {
//    }
//
//    public MService(String id, String service_name, String description) {
//        this.id = id;
//        this.service_name = service_name;
//        this.description = description;
//    }
}
