package com.shareeverything.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shareeverything.Util.ShareEverythingUtils;
import com.shareeverything.entity.*;
import com.shareeverything.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class AppInitialSeeder implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CityService cityService;

    @Autowired
    ZoneService zoneService;

    @Autowired
    ChargingScheduleService chargingScheduleService;

    @Autowired
    SubCategoryService subCategoryService;


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        seedCategories();
        seedCities();
        seedZone();
        seedChargingSchedule();
        seedSubCategories();
    }


    private void seedCategories() {
        List<Category> categoryList = categoryService.getAllCategory();
        if (categoryList.isEmpty()) {
            log.info("SEEDING CATEGORY LIST");

            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = ShareEverythingUtils.readStringFromFile("seed-data/categories.json");
                List<Category> categories= mapper.readValue(jsonString, new TypeReference<List<Category>>() {
                });
                categoryService.saveCategories(categories);
                log.info("CATEGORY SEEDING FINISHED");

            } catch (Exception e) {
                log.error("ERROR IN SEEDING CATEGORY", e);
            }
        }
    }


    private void seedCities() {
        List<City> cityList = cityService.getAllCity();
        if (cityList.isEmpty()) {
            log.info("SEEDING CITY LIST");

            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = ShareEverythingUtils.readStringFromFile("seed-data/city.json");
                List<City> cities= mapper.readValue(jsonString, new TypeReference<List<City>>() {
                });
                cityService.saveCity(cities);
                log.info("CITY SEEDING FINISHED");

            } catch (Exception e) {
                log.error("ERROR IN SEEDING CITY", e);
            }
        }
    }

    private void seedZone() {
        List<Zone> zoneList = zoneService.getAllZones();
        if (zoneList.isEmpty()) {
            log.info("SEEDING ZONE LIST");

            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = ShareEverythingUtils.readStringFromFile("seed-data/zone.json");
                List<Zone> zones= mapper.readValue(jsonString, new TypeReference<List<Zone>>() {
                });
                zoneService.saveZone(zones);
                log.info("ZONE SEEDING FINISHED");

            } catch (Exception e) {
                log.error("ERROR IN SEEDING ZONE", e);
            }
        }
    }

    private void seedChargingSchedule() {
        List<ChargingSchedule> chargingScheduleList = chargingScheduleService.getAllChargingSchedule();
        if (chargingScheduleList.isEmpty()) {
            log.info("SEEDING CHARGING SCHEDULE");

            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = ShareEverythingUtils.readStringFromFile("seed-data/charging-schedule.json");
                List<ChargingSchedule> chargingSchedules= mapper.readValue(jsonString, new TypeReference<List<ChargingSchedule>>() {
                });
                chargingScheduleService.saveCategories(chargingSchedules);
                log.info("CHARGING SCHEDULE SEEDING FINISHED");

            } catch (Exception e) {
                log.error("ERROR IN SEEDING CHARGING SCHEDULE", e);
            }
        }
    }

    private void seedSubCategories() {
        List<SubCategory> subCategoryList = subCategoryService.getAllSubCategory();
        if (subCategoryList.isEmpty()) {
            log.info("SEEDING SUB CATEGORY");

            ObjectMapper mapper = new ObjectMapper();
            try {
                String jsonString = ShareEverythingUtils.readStringFromFile("seed-data/sub-categories.json");
                List<SubCategory> subCategories= mapper.readValue(jsonString, new TypeReference<List<SubCategory>>() {
                });
                subCategoryService.saveSubCategories(subCategories);
                log.info("SUB CATEGORY SEEDING FINISHED");

            } catch (Exception e) {
                log.error("ERROR IN SEEDING SUB CATEGORY", e);
            }
        }
    }


}
