package com.example.bumblebee.controller;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.CartDao;
import com.example.bumblebee.model.entity.Cart;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.AddItemRequest;
import com.example.bumblebee.request.AddItemRequestWrapper;
import com.example.bumblebee.request.AddOrderItemRequest;
import com.example.bumblebee.request.UpdateCartItem;
import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.service.CartItemService;
import com.example.bumblebee.service.CartService;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")

public class CartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private CartDao cartDao;
    @GetMapping("/user")
    public ResponseEntity<Cart>getCartByUser(@RequestHeader("Authorization") String jwt) throws UserException {
       User user  = userService.findUserProfileByJwt(jwt);
        Cart cart=cartDao.findByUserId(user.getId());

        return new ResponseEntity<>(cart, HttpStatus.ACCEPTED);
    }

    @PutMapping("/add")
    public ResponseEntity<Cart>addItemToCart(@RequestBody AddItemRequest req,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Cart cart = cartService.addCartItem(user, req);

        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PostMapping("/add/demo")
    public ResponseEntity<Cart>addItemTOrder(@RequestBody AddOrderItemRequest req,
                                                    @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.demo(user, req.getAddItemRequest(), req.getCartItems());
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }



    @DeleteMapping("/cartItem/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCartItems(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
         cartItemService.deleteCartItems(user.getId(), cartItemId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Delete item");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @DeleteMapping("/cartItem/delete")
    public ResponseEntity<Cart>removeItemToCart(@RequestBody int[] cartItemIds, @RequestHeader("Authorization") String jwt) throws UserException, CartException {
        User user = userService.findUserProfileByJwt(jwt);

        System.out.println(cartItemIds);
        System.out.println("abc hello");
        for (int number : cartItemIds) {
            System.out.println(number);
        }
        Cart cart = cartItemService.removeCartItem(user.getId(), cartItemIds);

        return new ResponseEntity<>(cart, HttpStatus.ACCEPTED);
    }

    @PutMapping("/cartItem/{cartItemId}/update")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt, @RequestBody UpdateCartItem req) throws Exception {
        User user =userService.findUserProfileByJwt(jwt);
        cartItemService.updateCartItem(user, cartItemId, req.isStatus(), req.getQuantity());
        ApiResponse res = new ApiResponse();
        res.setMessage("Update CartItem Success");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/test")
    public ResponseEntity<Cart> test(@RequestBody AddItemRequestWrapper req) throws UserException, ProductException {
        if (req.getCart() == null){
            req.setCart(new Cart());
        }
        Cart test = cartService.test(req.getUser(), req.getCart(), req.getReq());
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

}
