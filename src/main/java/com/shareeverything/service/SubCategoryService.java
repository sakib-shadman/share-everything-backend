package com.shareeverything.service;

import com.shareeverything.entity.Category;
import com.shareeverything.entity.SubCategory;
import com.shareeverything.repository.CategoryRepository;
import com.shareeverything.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    public void saveSubCategories(List<SubCategory> subCategories){

        subCategoryRepository.saveAll(subCategories);

    }

    public List<SubCategory> getAllSubCategory(){
        return subCategoryRepository.findAll();
    }
}
