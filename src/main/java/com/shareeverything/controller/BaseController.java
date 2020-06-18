package com.shareeverything.controller;

import com.shareeverything.security.JwtTokenUtils;
import com.shareeverything.security.SecurityContextDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    SecurityContextDetails getSecurityContextDetails() {
        return (SecurityContextDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
    }

    String getLoginUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "";
        }
        SecurityContextDetails details = (SecurityContextDetails) authentication.getDetails();
        if (details == null) {
            return "";
        }
        return details.getUserid();
    }

    String getLoginUserId(HttpServletRequest request, JwtTokenUtils tokenUtils) {
        String token = tokenUtils.resolveToken(request);
        if (StringUtils.isEmpty(token) || token.equalsIgnoreCase("undefined")) {
            return "";
        }
        String userId = tokenUtils.getUserId(token);
        return userId;
    }
}
