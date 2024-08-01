package com.example.bumblebee.service;

import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.model.entity.Rating;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.RatingRequest;

import java.util.List;


public interface RatingService {
    public Rating createRating(RatingRequest req, User user) throws ProductException;
    public List<Rating>getProductsRating(Long productId);
}
