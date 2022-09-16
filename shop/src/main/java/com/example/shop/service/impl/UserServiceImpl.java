package com.example.shop.service.impl;

import com.example.shop.entity.Repository.UserRepository;
import com.example.shop.entity.dto.UserDto;
import com.example.shop.entity.enam.Role;
import com.example.shop.entity.model.User;
import com.example.shop.service.UserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with name: " + username);
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getRoles().toString()));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles);
    }

    @Override
    public boolean save(UserDto userDto) {
        if (!Objects.equals(userDto.getPassword(), userDto.getRepeatPassword())) {
            throw new RuntimeException("Password is not equal");
        }
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .roles(Collections.singletonList(Role.ROLE_USER))
                .build();
        userRepository.save(user);
        return true;
    }


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveInfo(User user) {
        User user1 = userRepository.findByUsername(user.getUsername());
        user1.setAddress(user.getAddress());
        user1.setCity(user.getCity());
        user1.setCountry(user.getCountry());
        user1.setPhoneNumber(user.getPhoneNumber());
        return userRepository.save(user1);
    }


}
