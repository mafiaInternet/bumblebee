package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface OrderItemDao extends JpaRepository<OrderItem, Long> {
//    @Query("SELECT o, a FROM Order o, Address a WHERE o.id = :orderId AND (o.address = :addressId AND a.id = :addressId)")
//    public List<Object[]> getOrder(@Param("addressId") long addressId, @Param("orderId") long orderId);
}
