package com.shareeverything.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.shareeverything.security.SecurityContextDetails;
import com.shareeverything.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ZoneController extends BaseController {

    @Autowired
    ZoneService zoneService;

    @RequestMapping(value = "/all-zone", method = RequestMethod.GET)
    public ResponseEntity<?> getAllZone() {
        SecurityContextDetails contextDetails = getSecurityContextDetails();
        return ResponseEntity.ok(zoneService.getAllZones());
    }
}
