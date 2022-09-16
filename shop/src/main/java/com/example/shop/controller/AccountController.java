package com.example.shop.controller;

import com.example.shop.entity.model.User;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AccountController {

    private  final UserService userService;

    @GetMapping(value = "account")
    private String accountHome(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        model.addAttribute("user", user);
        return "account";
    }

    @RequestMapping(value = "/update-info",method = {RequestMethod.GET, RequestMethod.POST})
    public String updateUser(
            @ModelAttribute("user") User user,
            Model model,
            RedirectAttributes redirectAttributes,
            Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
       User userSaved = userService.saveInfo(user);
        redirectAttributes.addFlashAttribute("user", userSaved);
        return "redirect:/account";
    }
}
