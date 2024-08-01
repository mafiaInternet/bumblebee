package com.example.bumblebee.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CategorySold {
    private String name;
    private long totalPrice;
    private int totalQuantity;
}
