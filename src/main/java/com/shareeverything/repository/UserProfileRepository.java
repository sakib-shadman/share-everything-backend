package com.shareeverything.repository;

import com.shareeverything.entity.User;
import com.shareeverything.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,UUID> {

    UserProfile findByUser(User user);
}
