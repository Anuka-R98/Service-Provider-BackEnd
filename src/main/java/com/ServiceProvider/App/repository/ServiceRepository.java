package com.ServiceProvider.App.repository;

import com.ServiceProvider.App.models.MService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ServiceRepository extends MongoRepository<MService, String> {

    Optional<MService> findByName(String name);

    List<MService> findByUserId(String userId);

}
