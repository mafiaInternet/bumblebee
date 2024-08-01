package com.example.bumblebee.service.Impl;

import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.model.dao.RatingDao;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.model.entity.Rating;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.RatingRequest;
import com.example.bumblebee.service.ProductService;
import com.example.bumblebee.service.RatingService;
import com.example.bumblebee.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class RatingServiceImpl implements RatingService {
    private UserService userService;
    private ProductService productService;
    private RatingDao ratingDao;
    public RatingServiceImpl(ProductService productService, RatingDao ratingDao){
        this.productService = productService;
        this.ratingDao = ratingDao;
    }
    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product=productService.findProductById(req.getProductId());

        Rating rating= new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setCreateAt(LocalDateTime.now());

        return ratingDao.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingDao.getAllProductsRating(productId);
    }
}
