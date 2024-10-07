package com.lcwd.user.service.UserService.services.impl;

import com.lcwd.user.service.UserService.entity.Hotel;
import com.lcwd.user.service.UserService.entity.Rating;
import com.lcwd.user.service.UserService.entity.User;
import com.lcwd.user.service.UserService.exception.ResourceNotFoundException;
import com.lcwd.user.service.UserService.external.service.HotelService;
import com.lcwd.user.service.UserService.external.service.RatingService;
import com.lcwd.user.service.UserService.repositories.UserRepo;
import com.lcwd.user.service.UserService.services.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RatingService ratingService;

//    private Logger logger = (Logger) LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @Override
    public User getUserById(String id) {
        User user = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id"+id+"not Found"));

//      *********        USING REST TEMPLATE CALLING ANOTHER SERVICE     **********

//        Rating[] ratingsArrayByThisId = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+id, Rating[].class);
//        List<Rating> ratingsByThisId = Arrays.stream(ratingsArrayByThisId).toList();

//        *********       USING FEIGN CLIENT          **********
        List<Rating> ratingsByThisId = ratingService.getratings(id);
        List<Rating> ratingList = ratingsByThisId.stream().map(rating->{
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//                 Hotel hotel = forEntity.getBody();
                   Hotel hotel = hotelService.getHotel(rating.getHotelId());
                   rating.setHotel(hotel);
                   return rating;
        }).collect(Collectors.toList());

//        user.setRatings(ratingsByThisId);
        user.setRatings(ratingList);
        return user;

    }
}
