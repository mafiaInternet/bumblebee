package com.example.bumblebee.service;

import com.example.bumblebee.exception.OrderException;
import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.exception.ReviewException;
import com.example.bumblebee.model.entity.Review;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.ReviewRequest;
import com.example.bumblebee.request.ReviewResponse;
import com.example.bumblebee.response.NumberOfEachTypeOfStartByProduct;

import java.util.List;

public interface ReviewService {
    List<Review> getReviewByProductId(Long productId, int rating);

    public Review createReview(ReviewRequest req, User user) throws ProductException, OrderException, ReviewException;

    Review createResponseReview(ReviewResponse res, User user);

    List<NumberOfEachTypeOfStartByProduct> getNumberOfEachTypeOfStar(Long productId);


    List<Review> getReviewByUser(User user);

    public List<Review> getAllReview(Long productId);
}
