package com.shareeverything.repository;

import com.shareeverything.constant.ProductStatus;
import com.shareeverything.entity.Post;
import com.shareeverything.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;
@Repository
public interface PostRepository extends JpaRepository<Post,UUID> {

    List<Post> findAllByUser(User user);
    Post findByIdAndUser(String id,User user);
    List<Post> findByStartDateLessThanEqualAndProductStatus(Date startDate, ProductStatus productStatus);
    Post findById(String id);
    Post findByIdAndProductStatus(String id,ProductStatus productStatus);
}
