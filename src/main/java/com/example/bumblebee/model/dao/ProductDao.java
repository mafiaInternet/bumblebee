package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
//    p.category.name LIKE CONCAT('%', :category, '%') OR
    @Query("SELECT p FROM Product p WHERE ( (p.category.name LIKE CONCAT('%', :category, '%') OR p.category.nameId LIKE CONCAT('%', :category, '%')) OR :category='') AND (p.title LIKE CONCAT('%', :title, '%') OR :title ='') ")
    public List<Product>filterProduct(@Param("category") String category,
                                      @Param("title") String title);

    @Query("SELECT p FROM Product p " + "WHERE p.category.nameId = :category" )
    public List<Product> getProductsByCategory(@Param("category") String category);
}
