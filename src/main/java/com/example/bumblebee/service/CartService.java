package com.example.bumblebee.service;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.entity.Cart;
import com.example.bumblebee.model.entity.CartItem;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.AddItemRequest;

public interface CartService {
//    public Cart createCart(User user);

    Cart createCart(Long userId) throws UserException;

    public Cart addCartItem(User userId, AddItemRequest addItemRequest) throws ProductException, UserException, CartException;

    Cart addCart(User user, AddItemRequest[] items) throws UserException, ProductException;

    Cart demo(User user, AddItemRequest req, CartItem[] cartItems) throws UserException, ProductException;


    Cart test(User user, Cart cart, AddItemRequest req) throws UserException, ProductException;
}
