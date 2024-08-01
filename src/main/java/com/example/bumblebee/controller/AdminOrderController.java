package com.example.bumblebee.controller;

import com.example.bumblebee.exception.OrderException;
import com.example.bumblebee.model.entity.Order;
import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;

    public AdminOrderController(OrderService orderService){
        this.orderService = orderService;
    }
    @GetMapping("/")
    public ResponseEntity<List<Order>>getAllOrdersHandlers(){
        List<Order>orders=orderService.getAllOrders();
        return new ResponseEntity<List<Order>>(orders,HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/confirmed")
    public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
                                                       @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.confirmedOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }


    @PutMapping("/{orderId}/deliver")
    public ResponseEntity<Order> DeliverOrderHandler(@PathVariable Long orderId,
        @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.deliveredOrder(orderId);

        return new ResponseEntity<Order>(order, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> CancelOrderHandler(@PathVariable Long orderId,
                                                    @RequestHeader("Authorization") String jwt) throws OrderException{
        Order order=orderService.canceledOrder(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{orderId}/delete")
    public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderId,
                                                          @RequestHeader("Authorization") String jwt) throws OrderException{
        orderService.deleteOrder(orderId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Order delete Success!!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{orderId}/{status}")
    public ResponseEntity<Order> statusOrderHandler(@PathVariable Long orderId, @PathVariable String status, @RequestHeader("Authorization") String jwt) throws OrderException{

        Order order = orderService.statusOrder(orderId, status);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }


}
