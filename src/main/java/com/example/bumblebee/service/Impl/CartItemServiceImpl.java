package com.example.bumblebee.service.Impl;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.CartDao;
import com.example.bumblebee.model.dao.CartItemDao;
import com.example.bumblebee.model.entity.Cart;
import com.example.bumblebee.model.entity.CartItem;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.service.CartItemService;
import com.example.bumblebee.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    private CartItemDao cartItemDao;
    private UserService userService;
    private CartDao cartDao;

    public CartItemServiceImpl(CartItemDao cartItemDao, UserService userService, CartDao cartDao){
        this.cartItemDao = cartItemDao;
        this.userService = userService;
        this.cartDao = cartDao;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setColor(cartItem.getColor());
        cartItem.setImageUrl(cartItem.getImageUrl());
        cartItem.setQuantity(cartItem.getQuantity());
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());
        cartItem.setProduct(cartItem.getProduct());
        CartItem createdCartItem = cartItemDao.save(cartItem);

        return createdCartItem;
    }

    @Override
    public CartItem updateCartItem(User user, Long id, int quantity) throws CartException {
        Optional<CartItem> cartItem =cartItemDao.findById(id);
        CartItem item = cartItem.get();
        System.out.println(item.getUserId());
        System.out.println("user -" + user.getId());
        if(item.getUserId() == user.getId()){
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
    public Cart removeCartItem(Long userId, Long[] cartItemIds) throws CartException, UserException {
        Cart cart = cartDao.findByUserId(userId);
        for(long cartItemId: cartItemIds){
            cartItemDao.deleteById(cartItemId);

        }
        cart.setTotalItem(cart.getCartItems().size());
        return cartDao.save(cart);
    }

    @Override
    public void deleteCartItems(Long userId, Long cartItemId) throws CartException, UserException {
        cartItemDao.deleteById(cartItemId);
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartException {

        Optional<CartItem> opt=cartItemDao.findById(cartItemId);

        if(opt.isPresent()){
            return opt.get();
        }
        throw new CartException("Cart Item not found with id - " + cartItemId);


    }
}
