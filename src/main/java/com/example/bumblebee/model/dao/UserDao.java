package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email =:email ")
    public User findByEmail(@Param("email") String email);


}
