package com.ServiceProvider.App.repository;

import com.ServiceProvider.App.models.MService;
import com.ServiceProvider.App.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ServiceRepository extends MongoRepository<MService, String> {

    Optional<MService> findByServiceName(String service_name);

//    Boolean existsByServiceName(String service_name);

}
