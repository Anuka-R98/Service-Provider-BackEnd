package com.ServiceProvider.App.controller;

import com.ServiceProvider.App.models.User;
import com.ServiceProvider.App.payload.requests.ServiceRequest;
import com.ServiceProvider.App.payload.responses.ServiceResponse;
import com.ServiceProvider.App.repository.ServiceRepository;
import com.ServiceProvider.App.service.ServiceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {
    private static final Logger log = (Logger) LoggerFactory.getLogger(User.class);
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private ServiceService service;

    /* create a new service */
    @PostMapping("home/services")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICEP')")
    public ResponseEntity<?> addService(@RequestBody ServiceRequest serviceRequest) {
        try {
            ServiceResponse serviceResponse = service.createService(serviceRequest);
            return ResponseEntity.ok(serviceResponse);
        } catch (Exception e) {
            log.error("Error while creating service: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating service");
        }
    }
}
