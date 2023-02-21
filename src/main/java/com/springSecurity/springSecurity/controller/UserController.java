package com.springSecurity.springSecurity.controller;

import com.springSecurity.springSecurity.payload.requests.UserRequest;
import com.springSecurity.springSecurity.payload.responses.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class UserController {

//    @PostMapping("/users")
//    public UserResponse addUser(@RequestBody UserRequest userRequest){
//        try{
//
//        }catch()
//    }
//
//    @PutMapping("/users/{id}")
//    public ResponseEntity<UserResponse> updateStudent(@PathVariable String id, @RequestBody UserRequest userRequest){
//        try{
//            UserResponse userResponse = service.updateStudent(id, studentRequest);
//            return new ResponseEntity<>(studentResponse, HttpStatus.OK);
//        }catch(ResourceNotFoundException e){
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
}
