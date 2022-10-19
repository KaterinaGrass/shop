package com.example.shop.controller;

import com.example.shop.entity.model.Cart;
import com.example.shop.entity.model.Product;
import com.example.shop.entity.model.User;
import com.example.shop.service.CartService;
import com.example.shop.service.ProductService;
import com.example.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping(value = "/cart")
    public String cart(Model model, Principal principal, HttpSession session){
        if (principal == null){
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Cart cart = user.getCart();
        if (cart == null){
            model.addAttribute("check","No item in your cart");
        }
        model.addAttribute("subTotal", cart.getTotalPrice());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @PostMapping (value = "/add-to-cart")
    public String addItemToCart(
            @RequestParam("id") Integer productId,
            @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
            Principal principal,
            HttpServletRequest request) {
        if (principal == null){
            return "redirect:/login";
        }
        Product product = productService.getProductById(productId);
        String username = principal.getName();
        User user = userService.findByUsername(username);
        Cart cart = cartService.addItemToCart(product, quantity, user);
        return "redirect:" + request.getHeader("Referer");

    }

    @RequestMapping(value = "/update-cart",  method = RequestMethod.POST, params = "action=update")
    public String updateCar(
            @RequestParam("quantity") int quantity,
            @RequestParam("id") Integer productId,
            Model model,
            Principal principal){
        if (principal == null){
            return "redirect:/login";
        } else {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            Product product = productService.getProductById(productId);
            Cart cart = cartService.updateItemInCart(product, quantity, user);
            model.addAttribute("cart",cart);
            return "redirect:/cart";

        }
    }

    @RequestMapping(value = "/update-cart", method = RequestMethod.POST,params = "action=delete")
    public String deleteItemFromCart(
            @RequestParam("id") Integer productId,
            Model model,
            Principal principal){
        if (principal == null){
            return "redirect:/login";
        } else {
            String username = principal.getName();
            User user = userService.findByUsername(username);
            Product product = productService.getProductById(productId);
            Cart cart = cartService.deleteItemFromCart(product, user);
            model.addAttribute("cart",cart);
            return "redirect:/cart";

        }

    }
}
