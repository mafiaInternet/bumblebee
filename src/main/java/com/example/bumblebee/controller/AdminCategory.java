package com.example.bumblebee.controller;

import com.example.bumblebee.model.dao.CategoryDao;
import com.example.bumblebee.model.entity.Category;
import com.example.bumblebee.model.entity.User;
import com.example.bumblebee.response.ApiResponse;
import com.example.bumblebee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class AdminCategory {
    @Autowired
    private CategoryDao categoryDao;

    private UserService userService;
    @GetMapping("/abc")
    public ResponseEntity<List<Category>> getCategoryAll(){
        List<Category> listCategory = categoryDao.findAll();
        return new ResponseEntity<>(listCategory, HttpStatus.ACCEPTED);
    }

    @PostMapping("/category/add")
    public ResponseEntity<Category> addCategory(@RequestHeader("Authorization") String jwt, @RequestBody Category category) throws Exception {
        User user = userService.findUserAdmin(jwt);
        Category newCategory = new Category();
        newCategory.setNameId(category.getNameId());
        newCategory.setName(category.getName());
        categoryDao.save(newCategory);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@RequestHeader("Authorization") String jwt, Integer categoryId) throws Exception {
        User user = userService.findUserAdmin(jwt);

            categoryDao.deleteById(categoryId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Delete category success!!!");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
