package com.example.bumblebee.service;

import com.example.bumblebee.exception.CartException;
import com.example.bumblebee.exception.OrderException;
import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.entity.*;
import com.example.bumblebee.request.CreateOrderRequest;
import com.example.bumblebee.response.CategorySold;

import java.util.List;

public interface OrderService {
    Order getOrdersByUserYetCheckedout(User user);
    Order putStatusOrderByAdmin(Long orderId, String status) throws OrderException;
    public Order findOderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);
    Order addOrder(User user, CreateOrderRequest createOrderRequest) throws ProductException;
    public List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;
    List<CategorySold> getCategoryBestSold(User user);

    List<CategorySold> getCategoryMonth(User user);

    List<Order> getDemo(User user);

    List<CategorySold> getCategoryYear(User user);

    List<Integer> getToTalQuantityProductSold(int year);

    List<Long> getToTalPriceProductSold(int year);

    List<Order> findOrder(String email, Long id);

    List<Order> findOrdersByStatus(String status);
}
