package com.ServiceProvider.App.service;

import com.ServiceProvider.App.customExceptions.ResourceNotFoundException;
import com.ServiceProvider.App.models.MService;
import com.ServiceProvider.App.models.User;
import com.ServiceProvider.App.payload.requests.ServiceRequest;
import com.ServiceProvider.App.payload.responses.ServiceResponse;
import com.ServiceProvider.App.repository.ServiceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class ServiceService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(User.class);
    @Autowired
    private ServiceRepository serviceRepository;

    /* create new service */
    public ServiceResponse createService(ServiceRequest serviceRequest){
        /* creating a new service object */
        MService service = new MService();
        service.setService_name(serviceRequest.getService_name());
        service.setDescription(serviceRequest.getDescription());

        log.info("Creating new service : " + service.getService_name());
        serviceRepository.save(service);

        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setId(service.getId());
        serviceResponse.setService_name(service.getService_name());
        serviceResponse.setDescription(service.getDescription());

        return serviceResponse;
    }

    /* updating service details */
    public ServiceResponse updateService(String id, ServiceRequest serviceRequest){
        MService existingService = serviceRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Service with id : " + id + " not found ! "));

        existingService.setService_name(serviceRequest.getService_name());
        existingService.setDescription(serviceRequest.getDescription());

        log.info("Updating Service details of : " + existingService.getService_name());
        serviceRepository.save(existingService);

        ServiceResponse serviceResponse = new ServiceResponse();
        serviceResponse.setId(existingService.getId());
        serviceResponse.setService_name(existingService.getService_name());
        serviceResponse.setDescription(existingService.getDescription());

        return serviceResponse;
    }

    /* get all services */
    public List<MService> getServices() throws Exception {
        log.info("Getting all services");
        try {
            return serviceRepository.findAll();
        } catch (Exception e) {
            log.error("An error occurred while retrieving services: ", e);
            throw new Exception("An error occurred while retrieving services: " + e.getMessage());
        }
    }

    /* retrieve specific service using service id */
    public MService getServiceById(String id) throws Exception {
        log.info("Getting service with id: {}" + id);
        try {
            return serviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Service not found with id:" + id));
        } catch(Exception e) {
            log.error("An error occurred while retrieving the requested service", e);
            throw new Exception("An error occurred while retrieving the requested service" + e.getMessage());
        }
    }

    /* get specific service using name */
    public MService getServiceByName(String name) throws Exception {
        log.info("Getting service with id: {}" + name);
        try {
            return serviceRepository.findByServiceName(name).orElseThrow(() -> new ResourceNotFoundException("Service not found with service name:" + name));
        } catch(Exception e) {
            log.error("An error occurred while retrieving the requested service", e);
            throw new Exception("An error occurred while retrieving the requested service" + e.getMessage());
        }
    }

    /* delete a service */
    public String deleteService(String id) throws Exception {
        log.info("Deleting service with id " + id);
        try{
            serviceRepository.deleteById(id);
            return "User : " + id + " deleted successfully";
        } catch(Exception e) {
            log.error("An error occurred while deleting the requested service", e);
            throw new Exception("An error occurred while deleting the requested service" + e.getMessage());
        }
    }
}
