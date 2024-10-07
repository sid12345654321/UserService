package com.lcwd.user.service.UserService.repositories;

import com.lcwd.user.service.UserService.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,String> {

}
