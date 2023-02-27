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
//    public List<Rating> getRatings() throws Exception {
//        try {
//            List<Rating> ratings = ratingRepository.findAll();
//            log.info("Retrieving ratings successful");
//
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//            mapper.addMixIn(User.class, UserMixin.class);
//
//            String json = mapper.writeValueAsString(ratings);
//            return mapper.readValue(json, new TypeReference<List<Rating>>() {});
//        } catch (Exception e) {
//            log.error("An error occurred while retrieving ratings: ", e);
//            throw new Exception("An error occurred while retrieving ratings" + e.getMessage());
//        }
//    }

    /* get all ratings for a specific service */
    public List<Rating> getRatingsByServiceId(String serviceId) throws Exception {
        try{
            MService service = serviceService.getServiceById(serviceId);
            log.info("retrieving ratings for service: " + service.getName());
            return ratingRepository.findByService(service);
        }catch(Exception e) {
            throw new Exception("An error getting ratings for services.", e);
        }
    }

    /* updating rating details */
    public RatingResponse updateRating(String userId, String ratingId, RatingRequest ratingRequest){

        User existingUser = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User with id : " + userId + " not found ! "));
        Rating existingRating = ratingRepository.findById(ratingId).orElseThrow(()-> new ResourceNotFoundException("Rating with id : " + ratingId + " not found ! "));

        if(Objects.equals(existingRating.getUser().getId(), existingUser.getId())){

            log.info("User matched with rating !");
            existingRating.setRating(ratingRequest.getRating());
            existingRating.setComment(ratingRequest.getComment());

            log.info("Updating rating details for service name : " + existingRating.getService().getName());
            ratingRepository.save(existingRating);

            RatingResponse ratingResponse = new RatingResponse();
            ratingResponse.setId(existingRating.getId());
            ratingResponse.setUserId(existingRating.getUser().getId());
            ratingResponse.setUserName(existingRating.getUser().getUsername());
            ratingResponse.setServiceName(existingRating.getService().getName());
            ratingResponse.setServiceId(existingRating.getService().getId());
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
        log.info("Deleting service with id " + id);
        try{
            ratingRepository.deleteById(id);
            return "Rating : " + id + " deleted successfully";
        } catch(Exception e) {
            log.error("An error occurred while deleting the requested rating", e);
            throw new Exception("An error occurred while deleting the requested rating" + e.getMessage());
        }
    }

 }


