package com.fleet.scheduling.config;

import com.fleet.scheduling.service.interfaces.ScheduleCharging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScheduleChargingFactory {

    private final Map<String, ScheduleCharging> scheduleCharging;

    // Spring will inject a Map where the key is the bean name and the value is the service implementation
    @Autowired
    public ScheduleChargingFactory(Map<String, ScheduleCharging> scheduleCharging) {
        this.scheduleCharging = scheduleCharging;
    }

    public ScheduleCharging getScheduleCharging(String key) {
        return scheduleCharging.get(key);  // Dynamically select the service based on the key
    }
}
