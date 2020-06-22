package com.shareeverything.entity;

import com.shareeverything.constant.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BasicEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    User user;

    String title;

    String description;

    String location;

    String contactNo;

    Double amount;

    Zone zone;

    City city;

    Category category;

    SubCategory subCategory;

    ChargingSchedule chargingSchedule;

    Boolean isFree;

    @Enumerated(EnumType.STRING)
    ProductStatus productStatus;

    @Temporal(TemporalType.TIMESTAMP)
    Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date expireDate;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    Date updatedAt;


}
