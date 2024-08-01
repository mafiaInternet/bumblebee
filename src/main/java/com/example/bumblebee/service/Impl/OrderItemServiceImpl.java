package com.example.bumblebee.service.Impl;

import com.example.bumblebee.model.dao.OrderItemDao;
import com.example.bumblebee.model.entity.CartItem;
import com.example.bumblebee.model.entity.OrderItem;
import com.example.bumblebee.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemDao orderItemDao;


    @Override
    public OrderItem createOrderItem(CartItem cartItem) {
        OrderItem orderItem = new OrderItem();
        orderItem.setPrice(cartItem.getPrice());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setSize(cartItem.getSize());
        orderItem.setColor(cartItem.getColor());
        orderItem.setImageUrl(cartItem.getImageUrl());
        orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());
        orderItem.setProduct(cartItem.getProduct());
        return orderItemDao.save(orderItem);
    }




}
