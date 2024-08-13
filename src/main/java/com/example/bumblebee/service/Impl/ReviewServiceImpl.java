package com.example.bumblebee.service.Impl;

import com.example.bumblebee.exception.OrderException;
import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.exception.ReviewException;
import com.example.bumblebee.model.dao.ProductDao;
import com.example.bumblebee.model.dao.ReviewDao;
import com.example.bumblebee.model.dao.UserDao;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.model.entity.Review;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.request.ReviewRequest;
import com.example.bumblebee.request.ReviewResponse;
import com.example.bumblebee.response.NumberOfEachTypeOfStartByProduct;
import com.example.bumblebee.service.OrderService;
import com.example.bumblebee.service.ProductService;
import com.example.bumblebee.service.ReviewService;
import com.example.bumblebee.service.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private ReviewDao reviewDao;
    private ProductService productService;
    private ProductDao productDao;
    private UserService userService;
    private OrderService orderService;
    private UserDao userDao;
    public ReviewServiceImpl(ReviewDao reviewDao, ProductService productService, ProductDao productDao, OrderService orderService, UserService userService, UserDao userDao){
        this.reviewDao = reviewDao;
        this.productService = productService;
        this.productDao= productDao;
        this.orderService = orderService;
        this.userService = userService;
        this.userDao = userDao;
    }


    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Review review = new Review();
        Product product = productService.findProductById(req.getProductId());
        review.setRating(req.getRating());
        review.setName(user.getName());
        for (String item: req.getImageUrls()){
            System.out.println("imageUrl - " + item);
            review.getImageUrls().add(item);
        }
        review.setProduct(product);
        review.setDesciption(req.getDescription());
        review.setUser(user);
        review.setOrder(req.getOrder());
        review.setCreatedAt(LocalDateTime.now());
        review.setResponse("Chào bạn, Teelab cảm ơn bạn đã tin dùng và ủng hộ shop. Bạn cần tư vấn thêm thông tin hãy nhắn tin trực tiếp với Shop để được hỗ trợ nhé");
        Review saveReview = reviewDao.save(review);
        user.getReviews().add(review);
        userDao.save(user);

        return saveReview;
    }

    @Override
    public Review createResponseReview(ReviewResponse res, User user){

        Review review = reviewDao.getReviewById(res.getProductId(), res.getReviewId());
        review.setResponse(res.getResponse());
        return reviewDao.save(review);
    }

    @Override
    public List<Review> getReviewByProductId(Long productId, int rating){
        List<Review> reviews = new ArrayList<>();
        if(rating >= 1 && rating <= 5){

            reviews = reviewDao.getReviewByRating(productId, rating);
        }

        if(rating == 6){

            reviews = reviewDao.getAllProductsReview(productId);
        }

        if (rating == 0) {

            for (Review review : reviewDao.getAllProductsReview(productId)) {
                if (review.getImageUrls().size() > 0) {
                    reviews.add(review);
                }
            }
        }
        return reviews;
    }
    @Override
    public List<NumberOfEachTypeOfStartByProduct> getNumberOfEachTypeOfStar(Long productId){
        List<NumberOfEachTypeOfStartByProduct> list = new ArrayList<>();

        for (int i=6; i>=0; i--){
            NumberOfEachTypeOfStartByProduct item = new NumberOfEachTypeOfStartByProduct();
           List<Review> reviews = getReviewByProductId(productId, i);
           String star = null;
            if (i == 6){
                star = "Tất cả";
            } else if (i == 0) {
                star = "Có hình ảnh";
            }else {
                star = String.valueOf(i);
            }
            item.setStar(star);
            item.setQuantity(reviews.size());
            item.setReviews(reviews);
            list.add(item);
        }

        return list;
    }

    @Override
    public List<Review> getReviewByUser(User user){

        return user.getReviews();
    }

    @Override
    public List<Review> getAllReview(Long productId) {


        return reviewDao.getAllProductsReview(productId);
    }

}
