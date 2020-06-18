package com.shareeverything.service;

import com.shareeverything.constant.ResponseStatus;
import com.shareeverything.constant.Role;
import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.UserDto;
import com.shareeverything.dto.UserProfileDto;
import com.shareeverything.entity.User;
import com.shareeverything.entity.UserProfile;
import com.shareeverything.repository.UserProfileRepository;
import com.shareeverything.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    @Autowired
    PasswordService passwordService;

    @Autowired
    MapperFactory mapperFactory;


    @Override
    public void createUser(UserDto userDto) {

        User user = mapperFactory.getMapperFacade().map(userDto, User.class);
        String password = userDto.getPassword();
        if (!StringUtils.isEmpty(password)) {
            user.setPassword(passwordService.encode(password));
        }
        user.setFullName(userDto.getFirstName() + " " + userDto.getLastName());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        user.setRoles(roles);
        user.setCreatedAt(new Date());
        userRepository.save(user);

    }

    @Override
    public UserProfileDto getUserProfile(String userId) {

        User user = userRepository.getOne(UUID.fromString(userId));
        UserProfile userProfile = userProfileRepository.findByUser(user);

        if(userProfile == null){

            userProfile = UserProfile.builder()
                    .user(user)
                    .build();
            userProfileRepository.save(userProfile);
        }

        user = userProfile.getUser();
        UserProfileDto userProfileDto = UserProfileDto.builder()
                .userId(user.getId().toString())
                .email(user.getEmail())
                .address(userProfile.getAddress())
                .city(userProfile.getCity())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .mobileNo(user.getMobileNumber())
                .roles(user.getRoles())
                .zone(userProfile.getZone())
                .build();
        return userProfileDto;
    }

    @Override
    public BaseResponseDto updateUserProfile(UserProfileDto userProfileDto, String userId) {

        User user = userRepository.getOne(UUID.fromString(userId));
        UserProfile userProfile = userProfileRepository.findByUser(user);

        user = userProfile.getUser();

        user.setFullName(userProfileDto.getFullName());
        user.setFirstName(userProfileDto.getFirstName());
        user.setLastName(userProfileDto.getLastName());
        user.setMobileNumber(userProfileDto.getMobileNo());

        if(userProfile == null){
            userProfile = UserProfile.builder()
                    .user(user)
                    .createdAt(new Date())
                    .build();
        }

        userProfile.setAddress(userProfileDto.getAddress());
        userProfile.setCity(userProfileDto.getCity());
        userProfile.setZone(userProfileDto.getZone());
        userProfile.setUpdatedAt(new Date());

        user.setUpdatedAt(new Date());
        userProfile.setUser(user);

        userProfileRepository.save(userProfile);
        userRepository.save(user);

        return BaseResponseDto.builder()
                .message("User profile updated.")
                .status(ResponseStatus.SUCCESS)
                .build();
    }

    @Override
    public User getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        return user;
    }
}
