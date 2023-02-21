package com.springSecurity.springSecurity.security.services;

import com.springSecurity.springSecurity.repository.UserRepositiory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.logging.Logger;

public class UserService {
    private static final Logger log = (Logger) LoggerFactory.getLogger(User.class);
    @Autowired
    private UserRepositiory userRepositiory;

    // Save user

}
