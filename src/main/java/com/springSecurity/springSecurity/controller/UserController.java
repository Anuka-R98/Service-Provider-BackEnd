package com.springSecurity.springSecurity.controller;

import com.springSecurity.springSecurity.models.User;
import com.springSecurity.springSecurity.payload.requests.UserRequest;
import com.springSecurity.springSecurity.payload.responses.UserResponse;
import com.springSecurity.springSecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private static final Logger log = (Logger) LoggerFactory.getLogger(User.class);
    @Autowired
    private UserService service;

    /* saving user */
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')  or hasRole('SERVICEP')")
    public ResponseEntity<?> addUser(@RequestBody UserRequest userRequest) {
        //The <?> is a wildcard that allows us to return any type of response entity.
        try {
            UserResponse userResponse = service.saveUser(userRequest);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e) {
            // Log the error and return an error response
            log.error("Error while saving user: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while saving user");
        }
    }

    /* updating user */
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')  or hasRole('SERVICEP')")
    public ResponseEntity<UserResponse> updateStudent(@PathVariable String id, @RequestBody UserRequest userRequest){
        try{
            UserResponse userResponse = service.updateUser(id, userRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error updating user: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get users */
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')  or hasRole('SERVICEP')")
    public ResponseEntity<List<User>> getUser() {
        try {
            List<User> users = service.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error and return an error response
            log.error("Error while getting users: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /* get user by id */
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')  or hasRole('SERVICEP')")
    public ResponseEntity<User> findUserById(@PathVariable String id){
        try{
            User user = service.getUserbyId(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error getting user: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get user by name */
    @GetMapping("/users/{name}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')  or hasRole('SERVICEP')")
    public ResponseEntity<User> findUserByName(@PathVariable String name){
        try{
            User user = service.getUserbyName(name);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error getting user: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* delete user by id */
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')  or hasRole('SERVICEP')")
    public ResponseEntity<String> deleteStudent(@PathVariable String id){
        try{
            String response = service.deleteUser(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            log.error("Error deleting user: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
