package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND (o.orderStatus = 'PLACED' OR o.orderStatus = 'CONFIRMED' OR o.orderStatus = 'SHIPPED' OR o.orderStatus = 'DELIVERED')")
    public List<Order> getUsersOrders(@Param("userId") Long userId);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    public List<Order> getOrderByUser(@Param("userId") long userId);
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND o.orderStatus = null")
    public Order getUsersOrdersYetCheckedOut(@Param("userId") long userId);

    @Query("SELECT o FROM Order o WHERE o.user.email = :email AND o.id = :orderId")
    public List<Order> findOrders(@Param("email") String email, @Param("orderId") Long orderId);
}
