package com.lcwd.user.service.UserService.controller;


import com.lcwd.user.service.UserService.entity.User;
import com.lcwd.user.service.UserService.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
//        return new ResponseEntity<>(user1, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);


    }


    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user1 = userService.getUserById(userId);
//        return ResponseEntity.status(HttpStatus.FOUND).body(user1);
        return ResponseEntity.ok(user1);


    }

    public ResponseEntity<User> ratingHotelFallback(String userId,Exception es){
        User user = User.builder()
                        .email("dummy@dummy.com")
                        .name("dummy")
                        .userId("123dummy")
                        .build();
        return  new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){

        List<User> users = userService.getAllUser();
        return ResponseEntity.ok(users);
    }



}
