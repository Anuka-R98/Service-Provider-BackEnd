package com.ServiceProvider.App.payload.requests;

import lombok.Data;

@Data
public class RatingRequest {

    private String id;
    private double rating;
    private String comment;
    private String userId;
    private String serviceId;

}
