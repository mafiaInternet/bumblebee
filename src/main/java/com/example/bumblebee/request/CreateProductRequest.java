package com.example.bumblebee.request;


import com.example.bumblebee.model.entity.Color;

import com.example.bumblebee.model.entity.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateProductRequest {
    private String title;
    private List<String> listImageUrl = new ArrayList<>();
    private List<Color> colors = new ArrayList<>();
    private int price;
    private int discountedPrice;
    private int discountPersent;

    private int totalQuantity;
    private Category category;
    private String description;
}
