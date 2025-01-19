package com.fleet.scheduling.dto;

import com.fleet.scheduling.enums.VehicleType;
import lombok.Data;

@Data
public class Vehicle {
    private Integer id;
    private Double batteryCapacity; // in kWh
    private Double currentCharge; // in kWh
    private VehicleType vehicleType = VehicleType.TRUCK;

    public double getRequiredCharging() {
        return batteryCapacity - currentCharge;
    }
}
