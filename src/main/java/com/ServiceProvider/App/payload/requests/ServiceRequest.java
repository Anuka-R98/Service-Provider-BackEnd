package com.ServiceProvider.App.payload.requests;

import lombok.Data;

@Data
public class ServiceRequest {
    private String id;
    private String service_name;
    private String description;
}
