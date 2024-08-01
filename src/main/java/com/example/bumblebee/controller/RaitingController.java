package com.example.bumblebee.controller;

import com.example.bumblebee.model.entity.Rating;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.RatingRequest;
import com.example.bumblebee.service.RatingService;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RaitingController {
    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating>createRating(@RequestBody RatingRequest req,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Rating rating=ratingService.createRating(req, user);
        return new ResponseEntity<>(rating, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>>getProductsRating(@PathVariable Long productId,
                                                         @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        List<Rating> ratings=ratingService.getProductsRating(productId);

        return new ResponseEntity<>(ratings, HttpStatus.ACCEPTED);
    }
}
