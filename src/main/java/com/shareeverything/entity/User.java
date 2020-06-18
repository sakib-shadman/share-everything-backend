package com.shareeverything.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shareeverything.constant.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class User extends BasicEntity{

    String firstName;

    String lastName;

    String fullName;

    String email;

    String password;

    String mobileNumber;

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    Set<Role> roles;

    @OneToOne(fetch = FetchType.EAGER)
    UserProfile userProfile;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;


}
