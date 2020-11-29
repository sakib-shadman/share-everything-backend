package com.shareeverything.dto.response;

import com.shareeverything.entity.Category;
import com.shareeverything.entity.ChargingSchedule;
import com.shareeverything.entity.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostInfoDto {
    List<City> cities;
    List<Category> categories;
    List<ChargingSchedule> chargingSchedules;
}
