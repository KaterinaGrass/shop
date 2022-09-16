package com.example.shop.service;

import com.example.shop.entity.dto.UserDto;
import com.example.shop.entity.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {


    boolean save(UserDto userDto);
    User findByUsername (String username);
    User saveInfo(User user);


}
