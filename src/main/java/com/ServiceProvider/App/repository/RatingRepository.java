package com.ServiceProvider.App.repository;

import com.ServiceProvider.App.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
}
