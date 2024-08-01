package com.example.bumblebee.controller;

import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.request.CreateProductRequest;
import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> create(@RequestBody CreateProductRequest req) throws ProductException{
        Product product = productService.createProduct(req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }
    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) throws ProductException{
        productService.deleteProduct(productId);
        ApiResponse res= new ApiResponse();
        res.setMessage("Product delete Success!!!");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> findAllProducts(){
        List<Product> produts=productService.findAllProducts();

        return new ResponseEntity<>(produts, HttpStatus.ACCEPTED);
    }


    @PutMapping("/{product}/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product req,
                                                 @PathVariable Long productId) throws ProductException{
        Product product=productService.updateProduct(productId, req);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMultipleProduct(@RequestBody CreateProductRequest[] req){
        for(CreateProductRequest product:req){
            productService.createProduct(product);
        }
//        productService.createProduct(req);
        ApiResponse res=new ApiResponse();
        res.setMessage("Creates Success!!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody CreateProductRequest req){
//        System.out.println("hell " + req.getSizes().toString());
        productService.createProduct(req);
        ApiResponse res=new ApiResponse();
        res.setMessage("Creates Success!!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
