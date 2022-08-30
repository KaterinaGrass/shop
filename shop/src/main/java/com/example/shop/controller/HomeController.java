package com.example.shop.controller;

import com.example.shop.entity.dto.ProductDto;
import com.example.shop.entity.model.Category;
import com.example.shop.service.CategoryService;
import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    private String home (Model model) {
        List<Category> categories= categoryService.findByAll();
        List<ProductDto>productDtos = productService.findByAll();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDtos);
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
