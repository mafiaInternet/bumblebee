package com.example.bumblebee.model.dao;

import com.example.bumblebee.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewDao extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r WHERE r.product.id=:productId")
    public List<Review> getAllProductsReview(@Param("productId")Long productId);
    @Query("SELECT r FROM Review r WHERE r.product.id=:productId AND r.id=:reviewId")
    public Review getReviewById(@Param("productId")Long productId, @Param("reviewId") Long reviewId);
    @Query("SELECT r FROM Review r WHERE r.product.id=:productId AND r.rating=:rating")
    public List<Review> getReviewByRating(@Param("productId")Long productId, @Param("rating") float rating);
}
