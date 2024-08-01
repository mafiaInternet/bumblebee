package com.example.bumblebee.controller;

import com.example.bumblebee.exception.ProductException;
//import com.example.teelab.model.enitty.Color;
import com.example.bumblebee.model.dao.ProductDao;
import com.example.bumblebee.model.entity.Product;
//import com.example.teelab.model.enitty.Size;
import com.example.bumblebee.service.ProductService;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")

public class ProductController {

@Autowired
    private ProductService productService;
@Autowired
    private ProductDao productDao;
@Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> list = productDao.findAll();
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    @PostMapping("/sort/new")
    public ResponseEntity<List<Product>> sortProductsNew(@RequestBody List<Product> products){
        List<Product> data = productService.sortProductsNew(products);

        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }

    @PostMapping("/sort/old")
    public ResponseEntity<List<Product>> sortProductsOld(@RequestBody List<Product> products){
        List<Product> data = productService.sortProductsOld(products);

        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }


    @PostMapping("/sort/low")
    public ResponseEntity<List<Product>> sortProductLow(@RequestBody List<Product> products){

        List<Product> data = productService.sortProductByPriceLow(products);

        return new ResponseEntity<>(data, HttpStatus.ACCEPTED);
    }


    @PostMapping("/sort/high")
    public ResponseEntity<List<Product>> sortProductHigh(@RequestBody List<Product> products){
        System.out.println(products);
        List<Product> data = productService.sortProductByPriceHigh(products);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product>findProductByIdHandler(@PathVariable Long productId) throws ProductException{
        Product product=productService.findProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>>findProductByCategory(@PathVariable String category) throws ProductException{
        List<Product> res=  productService.findProductByCategory(category);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }


    @GetMapping("/search")
    public ResponseEntity<List<Product>> findProduct(@RequestParam("title") String title, @RequestParam("category") String category){
        List<Product> products = new ArrayList<>();

        if(category.equals("") && title.equals("")){
            products = productDao.findAll();
            System.out.println("product - all" );

        }else{

            products = productService.findProducts(category, title);
            System.out.println("product - find");
            System.out.println("category - " + category);
            System.out.println("title - " + title);
        }

        return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
    }

    @PostMapping("/category/creates")
    public void createCategory(){
        productService.createCategory();
    }
}
