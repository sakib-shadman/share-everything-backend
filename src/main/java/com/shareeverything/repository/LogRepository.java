package com.shareeverything.repository;

import com.shareeverything.entity.Category;
import com.shareeverything.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<Log,UUID> {
    
}
