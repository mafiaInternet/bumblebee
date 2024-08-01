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



    Order statusOrder(Long orderId, String status) throws OrderException;

    public Order findOderById(Long orderId) throws OrderException;

    Order deliveringOrder(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);


    CheckOut createCheckOut(User user, CreateOrderRequest createOrderRequest) throws UserException, CartException;


    List<Object[]> delete(User user);


//    OrderItem demo(Long userId, AddItemRequest req) throws ProductException, UserException;

//    public Order placedOrder(Long orderId) throws OrderException;


    //    @Override
//    public List<Object[]> delete(User user){
//        Address address = findAddressById(user);
//        Order isExistOrder = getOrdersByUserYetCheckedout(user);
//        return orderItemService.getOrder(address.getId(), isExistOrder.getId());
//    }
    Order addOrder(User user, CreateOrderRequest createOrderRequest) throws ProductException;

    Order pendingOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;
//    public Order shippedOrder(Long orderId) throws OrderException;
    public Order deliveredOrder(Long orderId) throws OrderException;
    public Order canceledOrder(Long orderId) throws OrderException;
    public List<Order> getAllOrders();
    public void deleteOrder(Long orderId) throws OrderException;




//    List<Order> getProductBestSold(User user);

    List<CategorySold> getCategoryBestSold(User user);

    List<CategorySold> getCategoryMonth(User user);

    List<Order> getDemo(User user);

    List<CategorySold> getCategoryYear(User user);

    List<Integer> getToTalQuantityProductSold(int year);

    List<Long> getToTalPriceProductSold(int year);

    List<Order> findOrder(String email, Long id);

    List<Order> findOrdersByStatus(String status);
}
