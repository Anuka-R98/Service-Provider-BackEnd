package com.ServiceProvider.App.payload.responses;

import com.ServiceProvider.App.models.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RatingResponse {
    private String id;
    private int rating;
    private String comment;
    private String serviceId;
    public String serviceName;
    private String userId;
    public String userName;

}
