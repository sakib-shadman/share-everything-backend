package com.shareeverything.repository;

import com.shareeverything.entity.Category;
import com.shareeverything.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category,UUID> {
    
}