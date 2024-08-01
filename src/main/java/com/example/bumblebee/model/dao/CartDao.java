package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id=:userId")
    public Cart findByUserId(@Param("userId")Long userId);
}
