package com.example.bumblebee.controller;

import com.example.bumblebee.exception.OrderException;
import com.example.bumblebee.model.entity.Order;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.service.OrderService;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    private final OrderService orderService;
    private final UserService userService;
    @Autowired
    public AdminOrderController(OrderService orderService, UserService userService){
        this.orderService = orderService;
        this.userService = userService;
    }
    @GetMapping("/")
    public ResponseEntity<List<Order>>getOrders(){
        List<Order>orders=orderService.getAllOrders();
        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> deleteOrderById(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Order delete Success!!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{id}/update")
    public ResponseEntity<ApiResponse> putStatusOrderByAdmin(@RequestHeader("Authorization") String jwt, @PathVariable long id, @RequestParam(name = "status") String status) throws Exception {
        User user = userService.findUserAdmin(jwt);
        Order order = orderService.putStatusOrderByAdmin(id, status);
        ApiResponse res = new ApiResponse();
        res.setMessage(status + " Success !!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }


}
