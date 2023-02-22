package com.ServiceProvider.App.service;

import com.ServiceProvider.App.customExceptions.ResourceNotFoundException;
import com.ServiceProvider.App.models.ERole;
import com.ServiceProvider.App.models.Role;
import com.ServiceProvider.App.models.User;
import com.ServiceProvider.App.payload.requests.UserRequest;
import com.ServiceProvider.App.payload.responses.UserResponse;
import com.ServiceProvider.App.repository.RoleRepository;
import com.ServiceProvider.App.repository.UserRepositiory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(User.class);
    @Autowired
    private UserRepositiory userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder encoder;

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

    /* updating user by admin */
    public UserResponse userUpdateAdmin(String id, UserRequest userRequest){
        User existingUser = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User with id : " + id + " not found ! "));

        existingUser.setUsername(userRequest.getUsername());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setPassword(encoder.encode(userRequest.getPassword()));
        existingUser.setEmail(userRequest.getEmail());

        Set<String> strRoles = userRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;

                    case "provider":
                        Role serviceProviderRole = roleRepository.findByName(ERole.ROLE_SERVICE_PROVIDER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(serviceProviderRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        existingUser.setRoles(roles);

        log.info("(admin) Updating User : " + existingUser.getUsername());
        userRepository.save(existingUser);

        UserResponse userResponse = new UserResponse();
        userResponse.setId(existingUser.getId());
        userResponse.setUsername(existingUser.getUsername());
        userResponse.setEmail(existingUser.getEmail());
        userResponse.setPassword(existingUser.getPassword());
        userResponse.setRoles(Collections.singleton(existingUser.getRoles().toString()));

        return userResponse;
    }

    /* get specific user using id */
    public User getUserbyId(String id) throws Exception {
        log.info("Getting user with id: {}" + id);
        try {
            return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + id));
        } catch(Exception e) {
            log.error("An error occurred while retrieving the requested user", e);
            throw new Exception("An error occurred while retrieving the requested user" + e.getMessage());
        }
    }

    /* get specific user using name */
    public User getUserbyName(String name) throws Exception {
        log.info("Getting user with id: {}" + name);
        try {
            return userRepository.findByUsername(name).orElseThrow(() -> new ResourceNotFoundException("User not found with name:" + name));
        } catch(Exception e) {
            log.error("An error occurred while retrieving the requested user", e);
            throw new Exception("An error occurred while retrieving the requested user" + e.getMessage());
        }
    }

    /* delete user by id */
    public String deleteUser(String id) throws Exception {
        log.info("Deleting user with idL " + id);
        try{
            userRepository.deleteById(id);
            return "User : " + id + " deleted successfully";
        } catch(Exception e) {
            log.error("An error occurred while deleting the requested user", e);
            throw new Exception("An error occurred while deleting the requested user" + e.getMessage());
        }
    }
}
