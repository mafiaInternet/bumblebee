package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorDao extends JpaRepository<Color, Integer> {
}
