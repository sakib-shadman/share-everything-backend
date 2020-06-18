package com.shareeverything.controller;


import com.shareeverything.constant.ResponseStatus;
import com.shareeverything.dto.BaseResponseDto;
import com.shareeverything.dto.request.LoginRequestDto;
import com.shareeverything.dto.response.LoginResponseDto;
import com.shareeverything.entity.User;
import com.shareeverything.security.JwtTokenUtils;
import com.shareeverything.service.PasswordService;
import com.shareeverything.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
@Slf4j
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordService passwordService;

    @Autowired
    JwtTokenUtils jwtTokenUtils;

    @RequestMapping(value = "/sign-in", method = RequestMethod.POST)
    public ResponseEntity<?> singIn(@RequestBody LoginRequestDto loginRequestDto) {

        loginRequestDto.setEmail(loginRequestDto.getEmail().toLowerCase());
        log.info("User email: {}", loginRequestDto.getEmail());
        User user = userService.getUserByEmail(loginRequestDto.getEmail());
        if (user == null) {
            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .message("User account not found.")
                    .status(ResponseStatus.FAILED)
                    .build());
        }

        if (!passwordService.matchPassword(loginRequestDto.getPassword(), user.getPassword())) {

            return ResponseEntity.badRequest().body(BaseResponseDto.builder()
                    .message("Invalid email/password")
                    .status(ResponseStatus.FAILED)
                    .build());
        }

        String token = jwtTokenUtils.generateToken(user);
        return ResponseEntity.ok().body(LoginResponseDto.builder()
                .id(user.getId().toString())
                .token(token)
                .email(user.getEmail())
                .fullName(user.getFullName())
                .build());
    }
}
