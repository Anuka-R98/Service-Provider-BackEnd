package com.ServiceProvider.App.payload.responses;

import lombok.Data;

@Data
public class ServiceResponse {
    private String id;
    private String service_name;
    private String description;
}
