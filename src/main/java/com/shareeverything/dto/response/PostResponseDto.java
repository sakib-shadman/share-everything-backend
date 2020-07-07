package com.shareeverything.dto.response;

import com.shareeverything.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {

    String id;

    String title;

    String description;

    String location;

    String startDate;

    String expireDate;

    String contactNo;

    Zone zone;

    City city;

    Category category;

    SubCategory subCategory;

    ChargingSchedule chargingSchedule;

    Boolean isfree;

    Double amout;


}
