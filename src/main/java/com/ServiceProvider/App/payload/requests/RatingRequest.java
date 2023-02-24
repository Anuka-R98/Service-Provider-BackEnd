package com.ServiceProvider.App.payload.requests;

import com.ServiceProvider.App.models.User;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
@Data
public class RatingRequest {

    private String id;
    private String serviceId;
    private int rating;
    private String comment;
    private String userId;

}
