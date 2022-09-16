package com.example.shop.service;

import com.example.shop.entity.model.Cart;

public interface OrderService {

    void saveOrder(Cart cart);
    void acceptOrder(Integer id);
    void cancelOrder(Integer id);
}
