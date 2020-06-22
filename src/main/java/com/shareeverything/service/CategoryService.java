package com.shareeverything.service;

import com.shareeverything.entity.Category;
import com.shareeverything.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public void saveCategories(List<Category> categories){

        categoryRepository.saveAll(categories);

    }

    public List<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
}
