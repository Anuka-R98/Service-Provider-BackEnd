package com.ServiceProvider.App.payload.requests;

import com.ServiceProvider.App.models.Rating;
import lombok.Data;

import java.util.List;

@Data
public class ServiceRequest {
    private String id;
    private String name;
    private String description;
    private String phoneNo;
    private double averageRating;

}
