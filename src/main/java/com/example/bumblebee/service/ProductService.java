package com.example.bumblebee.service;

import com.example.bumblebee.exception.ProductException;
//import com.example.teelab.model.enitty.Color;
import com.example.bumblebee.model.entity.Product;
//import com.example.teelab.model.enitty.Size;
import com.example.bumblebee.request.CreateProductRequest;

import java.util.List;

public interface ProductService {
    public Product createProduct(CreateProductRequest req);
    public String deleteProduct(Long producted) throws ProductException;
    public Product updateProduct(Long productId, Product req) throws ProductException;

    public Product findProductById(long productId) throws ProductException;

    public List<Product> findProductByCategory(String category) throws ProductException;

    public List<Product> findAllProducts();

    List<Product> findProducts(String title, String category);


    List<Product> getProductsNewByCategory();

    List<Product> sortProductByPriceHigh(List<Product> products);

    List<Product> sortProductByPriceLow(List<Product> products);

    List<Product> sortProductsNew(List<Product> products);

    List<Product> sortProductsOld(List<Product> products);

    void createCategory();
}
