package com.shareeverything.repository;

import com.shareeverything.entity.BookingInformation;
import com.shareeverything.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BookingInfoRepository extends JpaRepository<BookingInformation,UUID> {

    BookingInformation findById(String id);
    
}
