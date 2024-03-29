package com.ServiceProvider.App.controller;

import com.ServiceProvider.App.models.MService;
import com.ServiceProvider.App.payload.requests.ServiceRequest;
import com.ServiceProvider.App.payload.responses.ServiceResponse;
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
//@RequestMapping("home/services")
public class ServiceController {
    private static final Logger log = (Logger) LoggerFactory.getLogger(MService.class);

    @Autowired
    private ServiceService service;

    /* create a new service */
    @PostMapping("home/services")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICE_PROVIDER')")
    public ResponseEntity<?> addService(@RequestHeader("userId") String userId, @RequestBody ServiceRequest serviceRequest) {
        try {
            ServiceResponse serviceResponse = service.createService(userId, serviceRequest);
            return ResponseEntity.ok(serviceResponse);
        } catch (Exception e) {
            log.error("Error while creating service: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while creating service");
        }
    }

    /* updating service details by admin */
    @PutMapping("home/admin/services/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateServiceByAdmin(@PathVariable String id, @RequestBody ServiceRequest serviceRequest){
        try{
            ServiceResponse serviceResponse = service.updateServiceByAdmin(id, serviceRequest);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error updating service info: ", e);
            return new ResponseEntity<>("Service update failed", HttpStatus.NOT_FOUND);
        }
    }

    /* updating service details */
    @PutMapping("home/provider/services/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICE_PROVIDER')")
    public ResponseEntity<?> updateService(@PathVariable String id, @RequestBody ServiceRequest serviceRequest){
        try{
            ServiceResponse serviceResponse = service.updateService(id, serviceRequest);
            return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error updating service info: ", e);
            return new ResponseEntity<>("Service update failed", HttpStatus.NOT_FOUND);
        }
    }

    /* get all services */
    @GetMapping("home/services/all")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICEP') or ('USER')")
    public ResponseEntity<List<MService>> getServices() {
        try {
            List<MService> services = service.getServices();
            return new ResponseEntity<>(services, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while retrieving services: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /* get all service for a user */
    @GetMapping("home/services/userid/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICE_PROVIDER')")
    public ResponseEntity<List<MService>> getAllServicesByUser(@PathVariable String id){
        try{
            List<MService> services = service.getAllServicesByUser(id);
            return new ResponseEntity<>(services, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error getting service: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get service by id */
    @GetMapping("home/services/id/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICE_PROVIDER') or ('USER')")
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
    @GetMapping("home/services/name/{name}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICE_PROVIDER') or ('USER')")
    public ResponseEntity<MService> findServiceByName(@PathVariable String name){
        try{
            MService service2 = service.getServiceByName(name);
            return new ResponseEntity<>(service2, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error getting service: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* delete service by id */
    @DeleteMapping("home/services/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICE_PROVIDER')")
    public ResponseEntity<String> deleteService(@PathVariable String id){
        try{
            String response = service.deleteService(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error deleting service: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
