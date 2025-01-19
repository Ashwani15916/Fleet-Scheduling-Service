package com.fleet.scheduling.controller;

import com.fleet.scheduling.config.ScheduleChargingFactory;
import com.fleet.scheduling.request.ScheduleRequest;
import com.fleet.scheduling.service.interfaces.ScheduleCharging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.fleet.scheduling.enums.AlgorithmType.CLOSEST_SUM;

@RestController
@RequestMapping("/api/v1/charging/schedule")
public class ScheduleChargingController {

    @Autowired
    private ScheduleChargingFactory scheduleChargingFactory;

    @GetMapping(produces = "application/json")
    public Map<Integer, List<Integer>> getSchedule(@RequestBody ScheduleRequest scheduleRequest){
        ScheduleCharging scheduleCharging = scheduleChargingFactory.getScheduleCharging(CLOSEST_SUM.name());
        return scheduleCharging.scheduleCharging(scheduleRequest);
    }
}
