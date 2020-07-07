package com.shareeverything.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    String title;

    String description;

    String location;

    String startDate;

    String expireDate;

    String contactNo;

    String zoneId;

    String cityId;

    String categoryId;

    String subCategoryId;

    String chargingScheduleId;

    Boolean isfree;

    Double amout;


}
