package com.ServiceProvider.App.repository;

import com.ServiceProvider.App.models.ERole;
import com.ServiceProvider.App.models.Role;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<Role,String> {

    Optional<Role> findByName(ERole name);
}
