package com.shareeverything.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @NotNull(message = "title cannot be null")
    @NotEmpty(message = "title cannot be empty")
    String title;

    @NotNull(message = "description cannot be null")
    @NotEmpty(message = "description cannot be empty")
    String description;

    @NotNull(message = "location cannot be null")
    @NotEmpty(message = "location cannot be empty")
    String location;

    @NotNull(message = "start date cannot be null")
    @NotEmpty(message = "start date cannot be empty")
    String startDate;

    @NotNull(message = "expire date cannot be null")
    @NotEmpty(message = "expire date cannot be empty")
    String expireDate;

    @NotNull(message = "contact no cannot be null")
    @NotEmpty(message = "contact no cannot be empty")
    String contactNo;

    @NotNull(message = "zone cannot be null")
    @NotEmpty(message = "zone cannot be empty")
    String zoneId;

    @NotNull(message = "city cannot be null")
    @NotEmpty(message = "city cannot be empty")
    String cityId;

    @NotNull(message = "category cannot be null")
    @NotEmpty(message = "category cannot be empty")
    String categoryId;

    String subCategoryId;

    String chargingScheduleId;

    Boolean isFree;

    Double amount;


}
