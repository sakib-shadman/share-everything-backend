package com.shareeverything.service;

import com.shareeverything.entity.Category;
import com.shareeverything.entity.ChargingSchedule;
import com.shareeverything.repository.CategoryRepository;
import com.shareeverything.repository.ChargingScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChargingScheduleService {

    @Autowired
    ChargingScheduleRepository chargingScheduleRepository;

    public void saveCategories(List<ChargingSchedule> chargingSchedules){

        chargingScheduleRepository.saveAll(chargingSchedules);

    }

    public List<ChargingSchedule> getAllChargingSchedule(){
        return chargingScheduleRepository.findAll();
    }
}
