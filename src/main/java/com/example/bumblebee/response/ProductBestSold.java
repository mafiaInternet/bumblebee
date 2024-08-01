package com.example.bumblebee.response;

import com.example.bumblebee.model.entity.Product;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
public class ProductBestSold {
    private List<Product> listProduct;
    private int totalQuantitySold;
}
