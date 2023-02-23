package com.ServiceProvider.App.controller;

import com.ServiceProvider.App.models.MService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /* updating service details */
    @PutMapping("home/services/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICEP')")
    public ResponseEntity<?> updateService(@PathVariable String id, @RequestBody ServiceRequest serviceRequest){

//        if (serviceRepository.existsBySerName(serviceRequest.getService_name())) {
//            return ResponseEntity.badRequest().body("Error: Username is already taken!");
//        }
//        if (serviceRepository.existsByDes(serviceRequest.getDescription())) {
//            return ResponseEntity.badRequest().body("Error: Email is already in use!");
//        }
        try{
            ServiceResponse serviceResponse = service.updateService(id, serviceRequest);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error updating service info: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get all services */
    @GetMapping("home/services")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICEP') or ('USER')")
    public ResponseEntity<List<MService>> getServices() {
        try {
            List<MService> services = service.getServices();
            return new ResponseEntity<>(services, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while retrieving services: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /* get service by id */
    @GetMapping("home/services/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MService> findServiceById(@PathVariable String id){
        try{
            MService service1 = service.getServiceById(id);
            return new ResponseEntity<>(service1, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error getting service: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get service by name */
    @GetMapping("home/services/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MService> findServiceByName(@PathVariable String name){
        try{
            MService service2 = service.getServiceByName(name);
            return new ResponseEntity<>(service2, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error getting service: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
