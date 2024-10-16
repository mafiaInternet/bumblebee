package com.example.bumblebee.service.Impl;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.CartDao;
import com.example.bumblebee.model.dao.CartItemDao;
import com.example.bumblebee.model.entity.Cart;
import com.example.bumblebee.model.entity.CartItem;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.AddItemRequest;
import com.example.bumblebee.service.CartItemService;
import com.example.bumblebee.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService  {
    private CartItemDao cartItemDao;
    private UserService userService;
    private CartDao cartDao;

    public CartItemServiceImpl(CartItemDao cartItemDao, UserService userService, CartDao cartDao){
        this.cartItemDao = cartItemDao;
        this.userService = userService;
        this.cartDao = cartDao;
    }
    @Override
    public CartItem createCartItem(AddItemRequest req, Product product, User user, Cart cart) {
        CartItem cartItem = new CartItem();
        cartItem.setColor(req.getColor());
        cartItem.setSize(req.getSize());
        cartItem.setImageUrl(req.getImageUrl());
        cartItem.setQuantity(req.getQuantity());
        cartItem.setProduct(product);
        cartItem.setUserId(user.getId());
        cartItem.setCart(cart);
        return cartItemDao.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(User user, Long id,boolean status, int quantity) throws CartException {
        Optional<CartItem> cartItem =cartItemDao.findById(id);
        CartItem item = cartItem.get();
        if(item.getUserId() == user.getId()){
            item.setStatus(status);
            item.setQuantity(quantity);
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
            return cartItemDao.save(item);
        }
        return null;
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String color, String size, Long userId) {

        CartItem cartItem =cartItemDao.isCartItemExist(cart, product, color, size, userId);
        if(cartItem == null){
            return null;
        }
        return cartItem;
    }

    @Override
    public Cart removeCartItem(Long userId, int[] cartItemIds) throws CartException, UserException {
        Cart cart = cartDao.findByUserId(userId);
        for(long cartItemId: cartItemIds){
            cartItemDao.deleteById(cartItemId);
            Optional<CartItem> cartItem = cartItemDao.findById(cartItemId);
            cart.getCartItems().remove(cartItem.get());
        }
        cart.setTotalItem(cart.getCartItems().size());
        return cartDao.save(cart);
    }

    @Override
    public void deleteCartItems(Long userId, Long cartItemId) throws CartException, UserException {
        cartItemDao.deleteById(cartItemId);
    }



}
