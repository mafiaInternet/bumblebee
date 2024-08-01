package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeDao extends JpaRepository<Size,Integer> {
}
