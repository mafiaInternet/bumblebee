package com.example.bumblebee.service;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.entity.Cart;
import com.example.bumblebee.model.entity.CartItem;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.AddItemRequest;

import java.util.List;

public interface CartItemService {

    CartItem createCartItem(AddItemRequest req, Product product, User user, Cart cart);

    public CartItem updateCartItem(User user, Long id, boolean status, int quantity) throws CartException, UserException;

    public CartItem isCartItemExist(Cart cart, Product product, String color, String size, Long userId);
    public Cart removeCartItem(Long userId, int[] cartItemIds) throws CartException, UserException;

    public void deleteCartItems(Long userId, Long cartItemId) throws CartException, UserException;


}
