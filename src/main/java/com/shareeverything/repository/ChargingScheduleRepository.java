package com.shareeverything.repository;

import com.shareeverything.entity.ChargingSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ChargingScheduleRepository extends JpaRepository<ChargingSchedule, UUID> {

}
