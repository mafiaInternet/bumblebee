package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.CheckOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckOutDao extends JpaRepository<CheckOut, Integer> {
}
