package com.springSecurity.springSecurity.repository;

import com.springSecurity.springSecurity.models.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
}
