package com.ServiceProvider.App.service;

import com.ServiceProvider.App.customExceptions.ResourceNotFoundException;
import com.ServiceProvider.App.models.MService;
import com.ServiceProvider.App.models.Rating;
import com.ServiceProvider.App.models.User;
import com.ServiceProvider.App.payload.requests.RatingRequest;
import com.ServiceProvider.App.payload.responses.RatingResponse;
import com.ServiceProvider.App.repository.RatingRepository;
import com.ServiceProvider.App.repository.ServiceRepository;
import com.ServiceProvider.App.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RatingService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(Rating.class);
    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ServiceService serviceService;

//    // UserMixin class is created to filter out json properties in a response
//    @JsonIgnoreProperties("roles")
//    private static class UserMixin {}

    /* create new service */
    public RatingResponse addRating(String userId, String serviceId, RatingRequest ratingRequest) throws Exception {
        try{
            /* check if user id is correct */
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                throw new Exception("User not found");
            }
            User user = optionalUser.get();

            /* check if service id is correct */
            MService service = serviceRepository.findById(serviceId).orElseThrow(()-> new ResourceNotFoundException("Service with id : " + serviceId + " not found ! "));

            Rating rating = new Rating();
            rating.setRating(ratingRequest.getRating());
            rating.setComment(ratingRequest.getComment());
            rating.setServiceId(service.getId());
            rating.setUserId(user.getId());
            rating.setServiceName(service.getName());
            rating.setUserName(user.getUsername());

            ratingRepository.save(rating);
            // Update the service's average rating
            List<Rating> ratings = ratingRepository.findByServiceId(rating.getServiceId());
            service.setRatings(ratings);
            serviceRepository.save(service);


            log.info("Rating is added to service: " + service.getName() + " / By user: " + user.getUsername());

            RatingResponse ratingResponse = new RatingResponse();
            ratingResponse.setId(rating.getId());
            ratingResponse.setRating(rating.getRating());
            ratingResponse.setComment(rating.getComment());
            ratingResponse.setUserId(rating.getUserId());
            ratingResponse.setUserName(rating.getUserName());
            ratingResponse.setServiceId(rating.getServiceId());
            ratingResponse.setServiceName(rating.getServiceName());
            return ratingResponse;

        } catch(Exception e) {
            throw new Exception("An error occurred while saving the rating.", e);
        }
    }

    /* get all ratings */
    public List<Rating> getRatings() throws Exception {
        try{
            log.info("retrieving ratings: ");
            return ratingRepository.findAll();
        }catch(Exception e) {
            log.error("An error occurred while retrieving ratings: ", e);
            throw new Exception("An error occurred while retrieving ratings" + e.getMessage());
        }
    }

    /* get all ratings for a specific service */
    public List<Rating> getRatingsByServiceId(String serviceId) throws Exception {
        try{
            Rating rating = (Rating) ratingRepository.findByServiceId(serviceId);
            log.info("retrieving ratings for service: " + rating.getServiceName());
            return (List<Rating>) rating;
        }catch(Exception e) {
            throw new Exception("An error getting ratings for services.", e);
        }
    }

    /* get all ratings for a specific user */
    public List<Rating> getRatingsByUserId(String userId) throws Exception {
        try{
            List<Rating> rating = ratingRepository.findByUserId(userId);;
            log.info("retrieving ratings for user: " + userId);
            return rating;
        }catch(Exception e) {
            throw new Exception("An error getting ratings for services.", e);
        }
    }

    /* updating rating details */
    public RatingResponse updateRating(String userId, String ratingId, RatingRequest ratingRequest) throws Exception {

        User existingUser = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with id : " + userId + " not found ! "));
        Rating existingRating = ratingRepository.findById(ratingId).orElseThrow(()-> new ResourceNotFoundException("Rating with id : " + ratingId + " not found ! "));
        MService service = serviceService.getServiceById(existingRating.getServiceId());

        if(Objects.equals(existingRating.getUserId(), existingUser.getId())){

            log.info("User matched with rating !");
            existingRating.setRating(ratingRequest.getRating());
            existingRating.setComment(ratingRequest.getComment());

            log.info("Updating rating details for service name : " + existingRating.getServiceName());

            // Update the service's average rating
            List<Rating> ratings = ratingRepository.findByServiceId(existingRating.getServiceId());
            service.setRatings(ratings);

            serviceRepository.save(service);
            ratingRepository.save(existingRating);

            RatingResponse ratingResponse = new RatingResponse();
            ratingResponse.setId(existingRating.getId());
            ratingResponse.setUserId(existingRating.getUserId());
            ratingResponse.setUserName(existingRating.getUserName());
            ratingResponse.setServiceName(existingRating.getServiceName());
            ratingResponse.setServiceId(existingRating.getServiceId());
            ratingResponse.setComment(existingRating.getComment());
            ratingResponse.setRating(existingRating.getRating());

            return ratingResponse;
        }
        else
            log.error("User is not matching");
            return null;
    }

    /* delete a rating */
    public String deleteRating(String id) throws Exception {
        log.info("Deleting rating with id " + id);
        try{
            ratingRepository.deleteById(id);
            return "Rating : " + id + " deleted successfully";
        } catch(Exception e) {
            log.error("An error occurred while deleting the requested rating", e);
            throw new Exception("An error occurred while deleting the requested rating" + e.getMessage());
        }
    }
 }


