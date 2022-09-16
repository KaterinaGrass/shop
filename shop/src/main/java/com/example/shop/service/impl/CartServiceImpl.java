package com.example.shop.service.impl;

import com.example.shop.entity.Repository.CartItemRepository;
import com.example.shop.entity.Repository.CartRepository;
import com.example.shop.entity.model.Cart;
import com.example.shop.entity.model.CartItem;
import com.example.shop.entity.model.Product;
import com.example.shop.entity.model.User;
import com.example.shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart addItemToCart(Product product, int quantity, User user) {
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
        }
        Set<CartItem> cartItems = cart.getCartItem();
        CartItem cartItem = findCartItem(cartItems, product.getId());
        if (cartItems == null) {
            cartItems = new HashSet<>();
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCoastPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);
            }
        } else {
            if (cartItem == null) {
                cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setTotalPrice(quantity * product.getCoastPrice());
                cartItem.setQuantity(quantity);
                cartItem.setCart(cart);
                cartItems.add(cartItem);
                cartItemRepository.save(cartItem);

            } else {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItem.setTotalPrice(cartItem.getTotalPrice() + (quantity * product.getCoastPrice()));
                cartItemRepository.save(cartItem);

            }
        }
        cart.setCartItem(cartItems);

        int totalItems = totalItems(cart.getCartItem());
        double totalPrice = totalPrice(cart.getCartItem());

        cart.setTotalPrice(totalPrice);
        cart.setTotalItems(totalItems);
        cart.setUser(user);


        return cartRepository.save(cart);

    }

    @Override
    public Cart updateItemInCart(Product product, int quantity, User user) {
        Cart cart = user.getCart();
        Set<CartItem>cartItems = cart.getCartItem();
        CartItem item = findCartItem(cartItems, product.getId());
        item.setQuantity(quantity);
        item.setTotalPrice(quantity * product.getCoastPrice());
        cartItemRepository.save(item);

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        return cartRepository.save(cart);
    }

    @Override
    public Cart deleteItemFromCart(Product product, User user) {
        Cart cart = user.getCart();
        Set<CartItem>cartItems = cart.getCartItem();
        CartItem item = findCartItem(cartItems, product.getId());
        cartItems.remove(item);
        cartItemRepository.delete(item);

        int totalItems = totalItems(cartItems);
        double totalPrice = totalPrice(cartItems);
        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);
        cart.setCartItem(cartItems);

        return cartRepository.save(cart);
    }


    private CartItem findCartItem(Set<CartItem> cartItems, Integer productId){
        if (cartItems == null){
            return null;
        }
        CartItem cartItem = null;
        for (CartItem item : cartItems){
            if (item.getProduct().getId() == productId){
                cartItem = item;
            }
        }
        return cartItem;
    }

    private int totalItems(Set<CartItem> cartItems){
        int totalItems = 0;
        for (CartItem item : cartItems){
            totalItems += item.getQuantity();
        }
        return totalItems;
    }

    private double totalPrice(Set<CartItem> cartItems){
        double totalPrice = 0.0;
        for (CartItem item : cartItems){
            totalPrice += item.getTotalPrice();
        }
        return totalPrice;
    }
}
