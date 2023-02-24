package com.ServiceProvider.App.controller;

import com.ServiceProvider.App.models.Rating;
import com.ServiceProvider.App.payload.requests.RatingRequest;
import com.ServiceProvider.App.payload.responses.RatingResponse;
import com.ServiceProvider.App.service.RatingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("home/ratings")
public class RatingController {

    private static final Logger log = (Logger) LoggerFactory.getLogger(Rating.class);
    @Autowired
    private RatingService ratingService;

    /* creating a rating */
    @PostMapping("home/services/ratings")
    @PreAuthorize("hasRole('ADMIN') or ('USER')")
    public ResponseEntity<RatingResponse> addRating(@RequestHeader("userId") String userId, @RequestBody RatingRequest ratingRequest) {
        try {
            RatingResponse ratingResponse = ratingService.addRating(userId, ratingRequest);
            return new ResponseEntity<>(ratingResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error adding rating: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}


