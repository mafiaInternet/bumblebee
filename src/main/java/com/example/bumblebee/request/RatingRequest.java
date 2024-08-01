package com.example.bumblebee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequest {
    private long productId;
    private double rating;
    private String comment;
}
