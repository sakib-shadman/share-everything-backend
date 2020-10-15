package com.shareeverything.controller;

import com.shareeverything.constant.ResponseStatus;
import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.UserDto;
import com.shareeverything.dto.UserProfileDto;
import com.shareeverything.entity.Log;
import com.shareeverything.entity.User;
import com.shareeverything.entity.UserProfile;
import com.shareeverything.repository.LogRepository;
import com.shareeverything.security.SecurityContextDetails;
import com.shareeverything.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserController extends BaseController{

    @Autowired
    UserServiceImpl userService;
    @Autowired
    LogRepository logRepository;

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {

        userDto.setEmail(userDto.getEmail().toLowerCase());
        User user = userService.getUserByEmail(userDto.getEmail());
        if (user != null) {

            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .message("User is already registered.")
                    .status(ResponseStatus.FAILED)
                    .build());
        }
        userService.createUser(userDto);
        return ResponseEntity.ok().body(BaseResponseDto.builder()
                .message("User has been registered.Please login.")
                .status(ResponseStatus.SUCCESS)
                .build());
    }

    @RequestMapping(value = "/get-user-profile", method = RequestMethod.GET)
    public ResponseEntity<?> getUserProfile() {

        SecurityContextDetails contextDetails = getSecurityContextDetails();
        return ResponseEntity.ok(userService.getUserProfile(contextDetails.getUserid()));
    }

    @RequestMapping(value = "/update-user-profile", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserProfile(@RequestBody UserProfileDto userProfileDto) {

        SecurityContextDetails contextDetails = getSecurityContextDetails();
        return ResponseEntity.ok(userService.updateUserProfile(userProfileDto,contextDetails.getUserid()));
    }
    @RequestMapping(value = "/post-log", method = RequestMethod.POST)
    public ResponseEntity<?> saveLog(@RequestBody Log log) {
        return ResponseEntity.ok(logRepository.save(log));
    }
    @RequestMapping(value = "/get-log", method = RequestMethod.GET)
    public ResponseEntity<?> getLog() {
        return ResponseEntity.ok(logRepository.findAll());
    }


}
