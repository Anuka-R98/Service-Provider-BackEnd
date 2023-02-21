package com.springSecurity.springSecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class PathController {

    @GetMapping("/home")
    public String userAccessHome() {
        return "This is home page";
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String userAccessDshboard() {
        return "This is dashboard";
    }

    @GetMapping("/manage")
    @PreAuthorize("hasRole('ADMIN')")
    public String userAccessManage() {
        return "This is dashboard";
    }

    @GetMapping("/profile/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccessProfile() {
        return "This is Admin profile";
    }

    @GetMapping("/profile/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')  or hasRole('SERVICEP')")
    public String userAccessProfile() {
        return "This is user profile";
    }

    @GetMapping("/services")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SERVICEP')")
    public String userServiceManage() {
        return "This is Service managing area";
    }

    @GetMapping("/ratings")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public String userRatings() {
        return "This is User rating area";
    }

}
