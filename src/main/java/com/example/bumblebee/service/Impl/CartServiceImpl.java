package com.example.bumblebee.service.Impl;

import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.CartDao;
import com.example.bumblebee.model.dao.CartItemDao;
import com.example.bumblebee.model.dao.ProductDao;
import com.example.bumblebee.model.entity.*;
import com.example.bumblebee.request.AddItemRequest;
import com.example.bumblebee.service.CartItemService;
import com.example.bumblebee.service.CartService;
import com.example.bumblebee.service.ProductService;
import com.example.bumblebee.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    private CartDao cartDao;
    private CartItemService cartItemService;
    private ProductService productService;
    private ProductDao productDao;
    private CartItemDao cartItemDao;

    private UserService userService;
    public CartServiceImpl(CartDao cartDao, CartItemService cartItemService, ProductService productService, CartItemDao cartItemDao, UserService userService, ProductDao productDao) {
        this.cartDao = cartDao;
        this.cartItemService = cartItemService;
        this.productService = productService;
        this.cartItemDao = cartItemDao;
        this.userService = userService;
        this.productDao = productDao;
    }

//    @Override
//    public Cart createCart(User user) {
//        return null;
//    }

    @Override
    public Cart createCart(Long userId) throws UserException {
        User user = userService.findUserById(userId);
        Cart cart = new Cart();
        cart.setUser(user);
        return cartDao.save(cart);
    }

    @Override
    public Cart addCartItem(User user, AddItemRequest req) throws ProductException, UserException {
        Cart cart = cartDao.findByUserId(user.getId());

        if(cart == null){
           cart = createCart(user.getId());
           cartDao.save(cart);
        }
        Product product=productService.findProductById(req.getProductId());


        CartItem isPresent=cartItemService.isCartItemExist(cart, product,req.getColor(), req.getSize(), user.getId());

        if(isPresent == null){
            CartItem cartItem=new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setColor(req.getColor());
            cartItem.setImageUrl(req.getImageUrl());
            double price=req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            cartItem.setUserId(user.getId());
            CartItem createdCartItem=cartItemService.createCartItem(cartItem);
//            cart.setCartItems((Set<CartItem>) createdCartItem);
            cart.getCartItems().add(createdCartItem);
            cart.setTotalItem(cart.getTotalItem() + 1);
            System.out.println("totalItem 1 -" + cart.getCartItems().size());
        }else {
            isPresent.setQuantity(isPresent.getQuantity() + 1);
            cartItemDao.save(isPresent);
        }
        System.out.println("totalItem 2 -" + cart.getCartItems().size());

        return  cartDao.save(cart);


    }


    @Override
    public Cart addCart(User user, AddItemRequest[] items) throws UserException, ProductException {
        Cart cart = new Cart();
        cart.setUser(user);
        double totalPrice= 0;
        double totalDiscountedPrice= 0;
        int totalItem = 0;
       for(AddItemRequest req: items){
           System.out.println("product id" + req.getProductId());
           Product product=productService.findProductById(req.getProductId());
               CartItem cartItem=new CartItem();
               cartItem.setProduct(product);
               cartItem.setCart(cart);
               cartItem.setQuantity(req.getQuantity());
               cartItem.setUserId(user.getId());
               cartItem.setColor(req.getColor());
               cartItem.setImageUrl(req.getImageUrl());
               double price=req.getQuantity()*product.getDiscountedPrice();
               cartItem.setPrice(price);
               cartItem.setSize(req.getSize());
           cartItem.setDiscountedPrice(product.getDiscountedPrice()*cartItem.getQuantity());

           totalPrice=totalPrice+cartItem.getPrice();
           totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
           totalItem=totalItem+cartItem.getQuantity();

               cart.getCartItems().add(cartItem);


       }

        cart.setTotalDiscountedPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setDiscount(totalPrice-totalDiscountedPrice);

        return cart;
    }

    @Override
    public Cart demo(User user, AddItemRequest req, CartItem[] cartItems) throws UserException, ProductException {
        Cart cart = new Cart();
        cart.setUser(user);
        double totalPrice= 0;
        double totalDiscountedPrice= 0;
        int totalItem = 0;
        if(req != null){
            System.out.println("product id" + req.getProductId());
            Product product=productService.findProductById(req.getProductId());
            CartItem cartItem=new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(req.getQuantity());
            cartItem.setUserId(user.getId());
            cartItem.setColor(req.getColor());
            cartItem.setImageUrl(req.getImageUrl());
            double price=req.getQuantity()*product.getDiscountedPrice();
            cartItem.setPrice(price);
            cartItem.setSize(req.getSize());
            cartItem.setDiscountedPrice(product.getDiscountedPrice()*cartItem.getQuantity());

            totalPrice=totalPrice+cartItem.getPrice();
            totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
            totalItem=totalItem+cartItem.getQuantity();

            cart.getCartItems().add(cartItem);

            System.out.println("Cart 1- " + cart);
        } else if (cartItems != null) {
            for (CartItem item: cartItems){
                totalPrice=totalPrice+item.getPrice()*item.getQuantity();
                totalDiscountedPrice=totalDiscountedPrice+item.getDiscountedPrice()*item.getQuantity();
                totalItem=totalItem+item.getQuantity();
                cart.getCartItems().add(item);
            }
        }else{
            return cart;
        }
        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);
        cart.setTotalDiscountedPrice(totalDiscountedPrice);

        System.out.println("Cart 2- " + cart);
        return cart;
    }
    @Override
    public Cart findCartByUserId(Long userId) {
        Cart cart =cartDao.findByUserId(userId);
//        double totalPrice=0;
//        double totalDiscountedPrice=0;
//        int totalItem=0;
//
//        for(CartItem cartItem :cart.getCartItems()){
//            totalPrice=totalPrice+cartItem.getPrice();
//            totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
//            totalItem=totalItem+cartItem.getQuantity();
//        }
//
//        cart.setTotalDiscountedPrice(totalDiscountedPrice);
//        cart.setTotalItem(totalItem);
//        cart.setTotalPrice(totalPrice);
//        cart.setDiscount(totalPrice-totalDiscountedPrice);
//        cartDao.save(cart);
        return cart;
    }


}
