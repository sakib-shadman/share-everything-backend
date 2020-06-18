package com.shareeverything.service;

import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.UserDto;
import com.shareeverything.dto.UserProfileDto;
import com.shareeverything.entity.User;
import com.shareeverything.entity.UserProfile;

public interface UserService {

    void createUser(UserDto userDto);

    UserProfileDto getUserProfile(String userId);

    BaseResponseDto updateUserProfile(UserProfileDto userProfileDto, String userId);

    User getUserByEmail(String email);
}
