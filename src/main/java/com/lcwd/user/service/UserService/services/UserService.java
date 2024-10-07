package com.lcwd.user.service.UserService.services;

import com.lcwd.user.service.UserService.entity.User;

import java.util.List;


public interface UserService {

    User saveUser(User user);

    List<User> getAllUser();

    User getUserById(String id);
}
