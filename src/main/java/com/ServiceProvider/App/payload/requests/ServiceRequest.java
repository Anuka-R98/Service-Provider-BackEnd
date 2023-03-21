package com.ServiceProvider.App.payload.requests;

import lombok.Data;

@Data
public class ServiceRequest {
    private String id;
    private String name;
    private String description;
    private String phoneNo;
    private double averageRating;
    private String userId;

}
