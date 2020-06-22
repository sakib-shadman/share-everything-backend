package com.shareeverything.service;

import com.shareeverything.entity.City;
import com.shareeverything.entity.Zone;
import com.shareeverything.repository.CityRepository;
import com.shareeverything.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {

    @Autowired
    ZoneRepository zoneRepository;

    public void saveZone(List<Zone> zones){

        zoneRepository.saveAll(zones);

    }

    public List<Zone> getAllZones(){
        return zoneRepository.findAll();
    }
}
