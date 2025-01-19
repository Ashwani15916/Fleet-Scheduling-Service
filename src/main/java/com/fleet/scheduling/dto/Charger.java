package com.fleet.scheduling.dto;

import lombok.Data;

@Data
public class Charger {

    Integer id;
    Double rate;
//    Double utilizedCharging= Double.valueOf(0);
    Double remainingCapacity;

//    public double getAvailableCapacity(int hours) {
//        return rate * hours - utilizedCharging;
//    }
//
//    public void addUtilizedCharging(double charge) {
//        utilizedCharging += charge;
//    }
}
