package com.example.bumblebee.service;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.entity.Cart;
import com.example.bumblebee.model.entity.CartItem;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.model.entity.User;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
//    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartException, UserException;

    public CartItem updateCartItem(User user, Long id, int quantity) throws CartException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String color, String size, Long userId);
    public Cart removeCartItem(Long userId, Long[] cartItemIds) throws CartException, UserException;

    public void deleteCartItems(Long userId, Long cartItemId) throws CartException, UserException;

    public CartItem findCartItemById(Long cartItemId) throws CartException;

}
