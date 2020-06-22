package com.shareeverything.repository;

import com.shareeverything.entity.Post;
import com.shareeverything.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,UUID> {
    
}
