package com.example.bumblebee.request;

import com.example.bumblebee.model.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ReviewRequest {
    private Long productId;
    private Order order;
    private List<String> imageUrls = new ArrayList<>();
    private String description;
    private int rating;
}
