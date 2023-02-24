package com.ServiceProvider.App.service;

import com.ServiceProvider.App.models.MService;
import com.ServiceProvider.App.models.Rating;
import com.ServiceProvider.App.models.User;
import com.ServiceProvider.App.payload.requests.RatingRequest;
import com.ServiceProvider.App.payload.responses.RatingResponse;
import com.ServiceProvider.App.repository.RatingRepository;
import com.ServiceProvider.App.repository.ServiceRepository;
import com.ServiceProvider.App.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(Rating.class);
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private ServiceService serviceService;


    /* create new service */
    public RatingResponse addRating(String userId, RatingRequest ratingRequest) throws Exception {
        try{
            User user = userService.getUserbyId(userId);
            MService service = serviceService.getServiceById(ratingRequest.getServiceId());

            Rating rating = new Rating();
            rating.setRating(ratingRequest.getRating());
            rating.setComment(ratingRequest.getComment());
            rating.setUser(user);
            rating.setService(service);

            ratingRepository.save(rating);
            log.info("Rating is added to service: " + service.getName() + " / By user: " + user.getUsername());
            log.info("Rating value: " + rating.getRating());

            RatingResponse ratingResponse = new RatingResponse();
            ratingResponse.setId(rating.getId());
            ratingResponse.setRating(rating.getRating());
            ratingResponse.setComment(rating.getComment());
            ratingResponse.setUserId(rating.getUser().getId());
            ratingResponse.setUserName(rating.getUser().getUsername());
            ratingResponse.setServiceId(rating.getService().getId());
            ratingResponse.setServiceName(rating.getService().getName());
            return ratingResponse;

        } catch(Exception e) {
            throw new Exception("An error occurred while saving the rating.", e);
        }

    }
 }
//    public List<Rating> getRatingsByServiceId(String serviceId) {
//        MService service = serviceService.getServiceById(serviceId);
//        return ratingRepository.findByService(service);
