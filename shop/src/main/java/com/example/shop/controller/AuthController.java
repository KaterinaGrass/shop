package com.example.shop.controller;

import com.example.shop.entity.dto.UserDto;
import com.example.shop.entity.model.User;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login1";
    }

    @GetMapping(value = "/register")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }


    @PostMapping("/do-register")
    public String saveUser(@Valid UserDto userDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        if (userService.save(userDto)) {
            return "redirect:/login";
        } else {
            model.addAttribute("user", userDto);
        }
        return "register";
    }

    }

