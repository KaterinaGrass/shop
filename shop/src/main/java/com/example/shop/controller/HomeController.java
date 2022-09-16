package com.example.shop.controller;


import com.example.shop.entity.dto.ProductDto;
import com.example.shop.entity.model.Cart;
import com.example.shop.entity.model.Category;
import com.example.shop.entity.model.User;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final UserService userService;


    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String home(Model model, Principal principal, HttpSession session){
        if(principal != null){
            session.setAttribute("username", principal.getName());
            User user = userService.findByUsername(principal.getName());
            Cart cart = user.getCart();
            session.setAttribute("totalItems", cart.getTotalItems());
        }else{
            session.removeAttribute("username");
        }
        return "home";
    }


    @GetMapping("/home")
    public String index(Model model){
        List<Category> categories = categoryService.findByAll();
        List<ProductDto> productDtos = productService.findByAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
        return "index";
    }
}
