package com.example.bumblebee.controller;

import com.example.bumblebee.exception.UserException;
import com.example.bumblebee.model.dao.ReviewDao;
import com.example.bumblebee.model.entity.Review;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.ReviewRequest;
import com.example.bumblebee.request.ReviewResponse;
import com.example.bumblebee.response.NumberOfEachTypeOfStartByProduct;
import com.example.bumblebee.service.ReviewService;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ReviewDao reviewDao;
    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Review review=reviewService.createReview(req, user);

        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @PostMapping("/api/create/response")
    public ResponseEntity<Review> createReviewResponse(@RequestBody ReviewResponse res, @RequestHeader("Authorization")String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createResponseReview(res, user);
        return new ResponseEntity<>(review, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<NumberOfEachTypeOfStartByProduct>> getProductReviews(@PathVariable Long productId)  {
        List<NumberOfEachTypeOfStartByProduct> reviews=reviewService.getNumberOfEachTypeOfStar(productId);

        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }

    @GetMapping("/product/{productId}/{rating}")
    public ResponseEntity<List<Review>> getReviewByRating(@PathVariable Long productId, @PathVariable int rating){
        List<Review> reviews = reviewService.getReviewByProductId(productId, rating);

        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Review>> getReviewByUser(@RequestHeader("Authorization") String jwt)throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Review> reviews = reviewService.getReviewByUser(user);

        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
