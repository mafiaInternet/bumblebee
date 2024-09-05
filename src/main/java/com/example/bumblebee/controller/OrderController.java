package com.example.bumblebee.controller;


import com.example.bumblebee.exception.OrderException;

import com.example.bumblebee.model.entity.*;

import com.example.bumblebee.request.CreateOrderRequest;

import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.response.CategorySold;
import com.example.bumblebee.service.OrderService;
import com.example.bumblebee.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
@Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Order> postOrder(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.addOrder(user, createOrderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<Order>>getOrders(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Order> orders=orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Order order=orderService.findOderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @GetMapping("/week")
    public ResponseEntity<List<CategorySold>> getOrderSoledWeek(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserAdmin(jwt);


        List<CategorySold> orders = orderService.getCategoryBestSold(user);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/month")
    public ResponseEntity<List<CategorySold>> getOrderSoledMonth(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserAdmin(jwt);


        List<CategorySold> orders = orderService.getCategoryMonth(user);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @GetMapping("/year")
    public ResponseEntity<List<CategorySold>> getOrderSoledYear(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserAdmin(jwt);


        List<CategorySold> orders = orderService.getCategoryYear(user);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{year}/prices")
    public ResponseEntity<List<Long>> getTotalPriceMonthOfYear(@RequestHeader("Authorization") String jwt ,@PathVariable int year) throws Exception {
//        User user = userService.findUserAdmin(jwt);
        List<Long> listTotalMonthOfYear = orderService.getToTalPriceProductSold(year);

        return new ResponseEntity<>(listTotalMonthOfYear, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Order>> getOrderFilter(@RequestHeader("Authorization") String jwt, @RequestParam("email") String email, @RequestParam("orderId") Long orderId){
        List<Order> orders = orderService.findOrder(email,orderId);
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }


    @GetMapping("/{year}/quantities")
    public ResponseEntity<List<Integer>> getTotalQuantityMonthOfYear(@RequestHeader("Authorization") String jwt ,@PathVariable int year) throws Exception {
        User user = userService.findUserAdmin(jwt);
        List<Integer> listTotalMonthOfYear = orderService.getToTalQuantityProductSold(year);

        return new ResponseEntity<>(listTotalMonthOfYear, HttpStatus.OK);
    }
}
