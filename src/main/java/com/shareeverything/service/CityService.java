package com.shareeverything.service;

import com.shareeverything.entity.Category;
import com.shareeverything.entity.City;
import com.shareeverything.repository.CategoryRepository;
import com.shareeverything.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    public void saveCity(List<City> cities){

        cityRepository.saveAll(cities);

    }

    public List<City> getAllCity(){
        return cityRepository.findAll();
    }
}
