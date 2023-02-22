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
}
