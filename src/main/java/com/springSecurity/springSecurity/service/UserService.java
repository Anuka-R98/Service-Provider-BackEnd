package com.springSecurity.springSecurity.service;

import com.springSecurity.springSecurity.customExceptions.ResourceNotFoundException;
import com.springSecurity.springSecurity.payload.requests.UserRequest;
import com.springSecurity.springSecurity.payload.responses.UserResponse;
import com.springSecurity.springSecurity.repository.UserRepositiory;
import com.springSecurity.springSecurity.models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(User.class);
    @Autowired
    private UserRepositiory userRepository;
    @Autowired
    PasswordEncoder encoder;

    /* saving user */
    public UserResponse saveUser(UserRequest userRequest){
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());

        log.info("Saving User : " + user.getUsername());
        userRepository.save(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());

        return userResponse;
    }

    /* updating user */
    public UserResponse updateUser(String id, UserRequest userRequest){
        User existingUser = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with id : " + id + " not found ! "));

        existingUser.setUsername(userRequest.getUsername());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(encoder.encode(userRequest.getPassword()));

        log.info("Updating User : " + existingUser.getUsername());
        userRepository.save(existingUser);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(existingUser.getId());
        userResponse.setUsername(existingUser.getUsername());
        userResponse.setEmail(existingUser.getEmail());
        userResponse.setPassword(existingUser.getPassword());

        return userResponse;
    }

    /* get all users */
    public List<User> getUsers() throws Exception {
        log.info("Getting all registered users");
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("An error occurred while retrieving users: ", e);
            throw new Exception("An error occurred while retrieving users: " + e.getMessage());
        }
    }

    /* get specific user using id */
    public User getUserbyId(String id) throws Exception {
        log.info("Getting user with id: {}" + id);
        try {
            return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));
        } catch(Exception e) {
            log.error("An erro occured while retriving the requested user", e);
            throw new Exception("An erro occured while retriving the requested user" + e.getMessage());
        }
    }

    /* get specific user using name */
    public User getUserbyName(String name) throws Exception {
        log.info("Getting user with id: {}" + name);
        try {
            return userRepository.findByUsername(name).orElseThrow(() -> new ResourceNotFoundException("User not found with name:" + name));
        } catch(Exception e) {
            log.error("An erro occured while retriving the requested user", e);
            throw new Exception("An erro occured while retriving the requested user" + e.getMessage());
        }
    }

    /* delete user by id */
    public String deleteUser(String id) throws Exception {
        log.info("Deleting user with idL " + id);
        try{
            userRepository.deleteById(id);
            return "User : " + id + " deleted successfully";
        } catch(Exception e) {
            log.error("An error occured while deleting the requested user", e);
            throw new Exception("An error occured while deleting the requested user" + e.getMessage());
        }
    }
}
