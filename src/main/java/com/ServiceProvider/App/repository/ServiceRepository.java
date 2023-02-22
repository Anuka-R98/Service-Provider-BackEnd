package com.ServiceProvider.App.repository;

import com.ServiceProvider.App.models.Service;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ServiceRepository extends MongoRepository<Service, String> {

}
