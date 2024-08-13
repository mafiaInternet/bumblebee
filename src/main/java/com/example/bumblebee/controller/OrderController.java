package com.example.bumblebee.controller;


import com.example.bumblebee.exception.OrderException;

import com.example.bumblebee.model.entity.*;

import com.example.bumblebee.request.CreateOrderRequest;

import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.response.CategorySold;
import com.example.bumblebee.service.OrderService;
import com.example.bumblebee.service.UserService;
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

    @GetMapping("/delete")
    public ResponseEntity<List<Object[]>> delete(@RequestHeader("Authorization") String jwt ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Object[]> abc = orderService.delete(user);

        return new ResponseEntity<>(abc, HttpStatus.ACCEPTED);
    }

    @GetMapping("/order")
    public ResponseEntity<Order> getOrderByUser(@RequestHeader("Authorization") String jwt ) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Order order = orderService.getOrdersByUserYetCheckedout(user);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }


    @PutMapping("/checkout")
    public ResponseEntity<CheckOut> checkout(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        CheckOut checkOut = orderService.createCheckOut(user, createOrderRequest);


        return new ResponseEntity<CheckOut>(checkOut, HttpStatus.CREATED);
    }

    @PostMapping("/demo")
    public ResponseEntity<Order> addOrder(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest createOrderRequest) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.addOrder(user, createOrderRequest);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }



    @GetMapping("/user")
    public ResponseEntity<List<Order>>usersOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Order> orders=orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Order> findOrderById(@PathVariable("id") Long orderId,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Order order=orderService.findOderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }


    @GetMapping("/")
    public ResponseEntity<List<Order>> getOrdersAll(){
        List<Order> orders= orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
    }

    @PostMapping("/checkedOut")
    public ResponseEntity<Order> checkedOut( @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.pendingOrder(orderService.getOrdersByUserYetCheckedout(user).getId());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PutMapping("/{orderId}/pending")
    public ResponseEntity<Order> pendingOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.pendingOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> confirmedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.confirmedOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancled")
    public ResponseEntity<Order> cancledOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order = orderService.canceledOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @DeleteMapping("/{orderId}/deleted")
    public ResponseEntity<ApiResponse> deletedOrderHandler(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);
        ApiResponse res = new ApiResponse();
        res.setStatus(true);
        res.setMessage("Order deleted success");
        return new ResponseEntity<>(res, HttpStatus.OK);
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
