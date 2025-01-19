package com.fleet.scheduling.request;

import com.fleet.scheduling.dto.Charger;
import com.fleet.scheduling.dto.Vehicle;
import lombok.Data;

import java.util.List;

@Data
public class ScheduleRequest {

    private List<Vehicle> vehicles;
    private List<Charger> chargers;
    private Integer hours;
}
