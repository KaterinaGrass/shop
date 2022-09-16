package com.example.shop.service.impl;

import com.example.shop.entity.Repository.CartItemRepository;
import com.example.shop.entity.Repository.CartRepository;
import com.example.shop.entity.Repository.OrderDetailsRepository;
import com.example.shop.entity.Repository.OrderRepository;
import com.example.shop.entity.model.Cart;
import com.example.shop.entity.model.CartItem;
import com.example.shop.entity.model.Order;
import com.example.shop.entity.model.OrderDetails;
import com.example.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDetailsRepository orderDetailsRepository;
    private final OrderRepository orderRepository;
    private  final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public void saveOrder(Cart cart) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setOrderStatus("Pending");
        order.setUser(cart.getUser());
        order.setTotalPrice(cart.getTotalPrice());
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        for (CartItem item : cart.getCartItem()){
            OrderDetails orderDetails = new OrderDetails();
            orderDetails.setOrder(order);
            orderDetails.setQuantity(item.getQuantity());
            orderDetails.setProduct(item.getProduct());
            orderDetails.setUnitPrice(item.getProduct().getCoastPrice());
            orderDetailsRepository.save(orderDetails);
            orderDetailsList.add(orderDetails);
            cartItemRepository.delete(item);
        }
        order.setOrderDetailsList(orderDetailsList);
        cart.setCartItem(new HashSet<>());
        cart.setTotalItems(0);
        cart.setTotalPrice(0);
        cartRepository.save(cart);
        orderRepository.save(order);
    }

    @Override
    public void acceptOrder(Integer id) {
        Order order = orderRepository.getById(id);
        order.setDeliveryDate(new Date());
        order.setOrderStatus("Shipping");
        orderRepository.save(order);}

    @Override
    public void cancelOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}
