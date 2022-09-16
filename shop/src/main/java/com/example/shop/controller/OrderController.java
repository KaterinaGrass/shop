package com.example.shop.controller;

import com.example.shop.entity.model.Cart;
import com.example.shop.entity.model.Order;
import com.example.shop.entity.model.User;
import com.example.shop.service.OrderService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping(value = "/checkout")
    public String checkout(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        if (user.getPhoneNumber().trim().isEmpty() || user.getAddress().trim().isEmpty() || user.getCity().trim().isEmpty()
                || user.getCountry().trim().isEmpty()){
            model.addAttribute("user", user);
            model.addAttribute("error", "You must fill the information after checkout");
            return "account";
        }else {
            model.addAttribute("user", user);
            Cart cart = user.getCart();
            model.addAttribute("cart",cart);

        }
        return "checkout";
    }

    @GetMapping(value = "/order")
    public String order(Principal principal, Model model){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        List<Order> orderList = user.getOrders();
        model.addAttribute("orders",orderList);
        return "order";
    }

    @GetMapping(value = "/save-order")
    public String saveOrder(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Cart cart = user.getCart();
        orderService.saveOrder(cart);
        return "redirect:/order";
    }

}
