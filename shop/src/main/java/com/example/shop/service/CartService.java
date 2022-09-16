package com.example.shop.service;

import com.example.shop.entity.model.Cart;
import com.example.shop.entity.model.Product;
import com.example.shop.entity.model.User;

public interface CartService {

    Cart addItemToCart(Product product, int quantity, User user);
    Cart updateItemInCart(Product product, int quantity, User user);
    Cart deleteItemFromCart(Product product, User user);
}
