package com.lcwd.user.service.UserService.external.service;

import com.lcwd.user.service.UserService.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "RATINGSERVICE")
public interface RatingService {

    @GetMapping("ratings/users/{userId}")
    List<Rating> getratings(@PathVariable String userId);
}
