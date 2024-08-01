package com.example.bumblebee.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ReviewResponse {
    private String response;
    private Long productId;
    private Long orderId;
    private Long reviewId;
}
