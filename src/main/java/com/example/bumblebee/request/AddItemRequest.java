package com.example.bumblebee.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddItemRequest {
    private Long productId;
    private String size;
    private String imageUrl;
    private String color;
    private int quantity;
    private double price;
}
