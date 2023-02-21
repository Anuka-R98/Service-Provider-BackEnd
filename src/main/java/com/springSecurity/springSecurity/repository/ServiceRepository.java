package com.springSecurity.springSecurity.repository;

import com.springSecurity.springSecurity.models.AService;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<AService, String> {

}
