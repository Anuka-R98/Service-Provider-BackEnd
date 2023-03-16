package com.ServiceProvider.App.repository;

import com.ServiceProvider.App.models.MService;
import com.ServiceProvider.App.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String> {
    List<Rating> findByServiceId(String serviceId);

    List<Rating> findByUserId(String userId);


}
