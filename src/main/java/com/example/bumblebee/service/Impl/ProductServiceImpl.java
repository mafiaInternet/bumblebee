package com.example.bumblebee.service.Impl;

import com.example.bumblebee.exception.ProductException;
import com.example.bumblebee.model.dao.CategoryDao;
import com.example.bumblebee.model.dao.ColorDao;
import com.example.bumblebee.model.dao.SizeDao;
import com.example.bumblebee.model.entity.Color;
import com.example.bumblebee.model.entity.Size;
import com.example.bumblebee.service.CategoryService;
import com.example.bumblebee.model.dao.ProductDao;
import com.example.bumblebee.model.entity.Category;
import com.example.bumblebee.model.entity.Product;
import com.example.bumblebee.request.CreateProductRequest;

import com.example.bumblebee.service.ProductService;
import com.example.bumblebee.service.UserService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductDao productDao;
    private final CategoryService categoryService;
    private final CategoryDao categoryDao;
    private final UserService userService;
    private final ColorDao colorDao;
    private final SizeDao sizeDao;
    public ProductServiceImpl(ProductDao productDao, UserService userService, CategoryService categoryService, CategoryDao categoryDao, ColorDao colorDao, SizeDao sizeDao){
        this.productDao = productDao;
        this.userService = userService;
        this.categoryService = categoryService;
        this.categoryDao = categoryDao;
        this.colorDao = colorDao;
        this.sizeDao = sizeDao;
    }
    @Override
    public Product createProduct(CreateProductRequest req) {

//        System.out.println(category);
        Product product=new Product();
        product.setTitle(req.getTitle());


        List<Color> colors = new ArrayList<>();
        for(Color color: req.getColors()){
            Color createColor = new Color();
            createColor.setImageUrl(color.getImageUrl());
            createColor.setName(color.getName());
            List<Size> sizes = new ArrayList<>();
            for(Size size : color.getSizes()){
                Size createSize = new Size();
                createSize.setName(size.getName());
                createSize.setQuantity(size.getQuantity());

                sizes.add(sizeDao.save(createSize));
            }
            createColor.setSizes(sizes);

            Color saveColor = colorDao.save(createColor);

            colors.add(saveColor);
        }

        product.setColors(colors);
        product.setDescription(req.getDescription());
        int discountdPrice = req.getPrice() * req.getDiscountPersent() / 100;
        product.setDiscountedPrice(discountdPrice);
        product.setDiscountPersent(req.getDiscountPersent());
        product.setPrice(req.getPrice());
        product.setTotalQuantity(req.getTotalQuantity());
        product.setCategory(req.getCategory());
        product.setListImageUrl(req.getListImageUrl());
        product.setCreateAt(LocalDateTime.now());

        Product savedProduct =productDao.save(product);

        return savedProduct;
    }

    @Override
    public String deleteProduct(Long productId) throws ProductException {
        Product product=findProductById(productId);
            productDao.delete(product);
        System.out.println();
        return "Product deleted Success!!!";
    }

    @Override
    public Product updateProduct(Long productId, Product req) throws ProductException {
        Product product = findProductById(productId);


        return productDao.save(product);
    }

    @Override
    public Product findProductById(long productId) throws ProductException {
//        Optional<Product> opt=productDao.findById(productId);
        for(Product product: productDao.findAll()){
            if(product.getId() == productId){
                return product;
            }
        }
        throw new ProductException("Product not found with id - " + productId);
    }

    @Override
    public List<Product> findProductByCategory(String category) {
        if(category.equals("all")){
            return productDao.findAll();
        }
        List<Product> products = productDao.getProductsByCategory(category);
        return products;
    }

    @Override
    public List<Product> findAllProducts() {


        return productDao.findAll();
    }
    @Override
    public List<Product> findProducts( String category, String title){
        List<Product> products = productDao.filterProduct(category, title);
        return products;

    }


    @Override
    public List<Product> getProductsNewByCategory(){

        List<Category> categorys = categoryDao.findAll();
//        ArrayList<Product> productsByCategoryNew = new ArrayList<>();
        List<Product> productsByCategoryNew = new ArrayList<>();


        for(Category category: categorys){
            List<Product> productList = productDao.getProductsByCategory(category.getNameId());

            Collections.sort(productList, Comparator.comparing(Product::getCreateAt));
            int i = 0;
            for (Product product: productList){
                if(i < 7){
                    productsByCategoryNew.add(product);
                    i++;
                }
            }
        }


    return productsByCategoryNew;
    }

    @Override
    public List<Product> sortProductByPriceHigh(List<Product> products){

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getPrice() - p2.getPrice();
            }
        });

        return products;
    }

    @Override
    public List<Product> sortProductByPriceLow(List<Product> products){

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getPrice() - p1.getPrice();
            }
        });

        return products;
    }

    @Override
    public List<Product> sortProductsNew(List<Product> products){

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p1.getCreateAt().compareTo(p2.getCreateAt());
            }
        });

        return products;
    }

    @Override
    public List<Product> sortProductsOld(List<Product> products){

        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return p2.getCreateAt().compareTo(p1.getCreateAt());
            }
        });

        return products;
    }


    @Override
    public void createCategory(){
       categoryService.createCategory();


    }

}
