package com.example.bumblebee.request;

import com.example.bumblebee.model.entity.Order;
import com.example.bumblebee.model.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class ReviewRequest {

    private Product product;
    private Order order;
    private String description;
    private int rating;
}
