package com.example.bumblebee.service;

import com.example.bumblebee.model.entity.Category;

public interface CategoryService {
    Category findByName(String name);

    void createCategory();
}
