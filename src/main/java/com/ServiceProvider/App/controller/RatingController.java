package com.ServiceProvider.App.controller;

import com.ServiceProvider.App.models.Rating;
import com.ServiceProvider.App.payload.requests.RatingRequest;
import com.ServiceProvider.App.payload.requests.ServiceRequest;
import com.ServiceProvider.App.payload.responses.RatingResponse;
import com.ServiceProvider.App.payload.responses.ServiceResponse;
import com.ServiceProvider.App.service.RatingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("home/ratings")
public class RatingController {

    private static final Logger log = (Logger) LoggerFactory.getLogger(Rating.class);
    @Autowired
    private RatingService ratingService;

    /* creating a rating */
    @PostMapping("home/services/ratings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<RatingResponse> addRating(@RequestHeader("userId") String userId, @RequestBody RatingRequest ratingRequest) {
        try {
            RatingResponse ratingResponse = ratingService.addRating(userId, ratingRequest);
            return new ResponseEntity<>(ratingResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding rating: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get all ratings */
    @GetMapping("home/services/ratings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICEP') or hasRole('USER')")
    public ResponseEntity<List<Rating>> getRatings() {
        try {
            List<Rating> ratings = ratingService.getRatings();
            return new ResponseEntity<>(ratings, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting ratings: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get all ratings for a specific service */
    @GetMapping("home/services/ratings/{serviceId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICEP') or hasRole('USER')")
    public ResponseEntity<List<Rating>> getRatingsInService(@PathVariable String serviceId) {
        try {
            List<Rating> ratings = ratingService.getRatingsByServiceId(serviceId);
            return new ResponseEntity<>(ratings, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error getting ratings: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* updating rating details */
    @PutMapping("home/services/ratings/{ratingId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> updateRating(@RequestHeader("userId") String userId, @PathVariable String ratingId, @RequestBody RatingRequest ratingRequest){
        try{
            RatingResponse ratingResponse = ratingService.updateRating(userId, ratingId, ratingRequest);
            return new ResponseEntity<>(ratingResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error updating rating info: ", e);
            return new ResponseEntity<>("Rating update failed", HttpStatus.NOT_FOUND);
        }
    }

    /* delete a rating by id */
    @DeleteMapping("home/services/ratings/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteService(@PathVariable String id){
        try{
            String response = ratingService.deleteRating(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error deleting rating: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}


