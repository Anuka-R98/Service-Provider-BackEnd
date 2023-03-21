package com.ServiceProvider.App.payload.responses;

import lombok.Data;

@Data
public class RatingResponse {
    private String id;
    private double rating;
    private String comment;
    private String serviceId;
    public String serviceName;
    private String userId;
    public String userName;

}
