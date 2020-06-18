package com.shareeverything.dto;


import com.shareeverything.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {

    String userId;

    String firstName;

    String lastName;

    String fullName;

    String email;

    String mobileNo;

    String address;

    String city;

    String zone;

    Set<Role> roles;
}
