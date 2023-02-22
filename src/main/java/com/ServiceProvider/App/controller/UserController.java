package com.ServiceProvider.App.controller;
import com.ServiceProvider.App.models.User;
import com.ServiceProvider.App.payload.requests.UserRequest;
import com.ServiceProvider.App.payload.responses.UserResponse;
import com.ServiceProvider.App.repository.UserRepositiory;
import com.ServiceProvider.App.service.UserService;

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
    @Autowired
    private UserRepositiory userRepository;

    /* saving user */
    @PostMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest){

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        try{
            UserResponse userResponse = service.updateUser(id, userRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error updating user: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* updating user by admin */
    @PutMapping("admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminUpdateUser(@PathVariable String id, @RequestBody UserRequest userRequest){

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        try{
            UserResponse userResponse = service.userUpdateAdmin(id, userRequest);
            return new ResponseEntity<>(userResponse, HttpStatus.OK);
        }catch(Exception e){
            log.error("Error updating user: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /* get users */
    @GetMapping("admin/users")
    @PreAuthorize("hasRole('ADMIN')")
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
    @GetMapping("admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @GetMapping("admin/users/{name}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @DeleteMapping("admin/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
