package com.fleet.scheduling.service.implementations;

import com.fleet.scheduling.dto.Charger;
import com.fleet.scheduling.dto.Vehicle;
import com.fleet.scheduling.request.ScheduleRequest;
import com.fleet.scheduling.service.interfaces.ScheduleCharging;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("CLOSEST_SUM")
public class ClosestSumScheduleCharging implements ScheduleCharging {

    @Override
    public Map<Integer, List<Integer>> scheduleCharging(ScheduleRequest scheduleRequest) {
        Map<Integer, List<Integer>> schedule = new HashMap<>();
        List<Vehicle> vehicles = scheduleRequest.getVehicles();
        List<Charger> chargers = scheduleRequest.getChargers();
        int hours = scheduleRequest.getHours();
        chargers.forEach(charger -> charger.setRemainingCapacity(charger.getRate() * hours));
        Map<Integer,Vehicle> remainingVehicles = new HashMap<>();
        Map<Integer,Charger> remainingChargers = new HashMap<>();
        for(Vehicle vehicle:vehicles){
            remainingVehicles.put(vehicle.getId(),vehicle);
        }
        for (Charger charger:chargers){
            remainingChargers.put(charger.getId(),charger);
        }

        assignVehicleToChargers(remainingVehicles,remainingChargers,schedule,chargers);
        return schedule;
    }

    private void assignVehicleToChargers(Map<Integer, Vehicle> vehicleMap, Map<Integer, Charger> chargerMap,
                                        Map<Integer, List<Integer>> schedule,List<Charger> chargers) {
        int pointer = 0;
        while (!chargers.isEmpty()) {
            Charger charger = chargers.get(pointer);
            double remainingCapacity = charger.getRemainingCapacity();

            // Find the trucks that can be assigned to this charger
            List<Vehicle> availableVehicles = new ArrayList<>(vehicleMap.values());
            List<Integer> assignedVehicleIds = findClosestVehicle(availableVehicles, remainingCapacity);
            if(assignedVehicleIds.isEmpty()){
                chargers.remove(charger);
            }
            // Update the schedule for the current charger
            List<Integer> chargerSchedule = schedule.getOrDefault(charger.getId(), new ArrayList<>());
            chargerSchedule.addAll(assignedVehicleIds);
            schedule.put(charger.getId(), chargerSchedule);

            // Calculate consumed energy and update remaining capacity
            double consumedEnergy = 0;
            for (Integer vehicleId : assignedVehicleIds) {
                Vehicle vehicle = vehicleMap.get(vehicleId);
                if (vehicle != null) {
                    consumedEnergy += vehicle.getRequiredCharging();
                    vehicleMap.remove(vehicleId);
                }
            }
            charger.setRemainingCapacity(remainingCapacity - consumedEnergy);
            pointer++;
            if(pointer>=chargers.size()){
                pointer = 0;
            }
        }
    }

    public List<Integer> findClosestVehicle(List<Vehicle> vehicles, double target) {
        if (vehicles == null || vehicles.isEmpty()) {
            return Collections.emptyList();
        }


        if (vehicles.size() == 1) {
            Vehicle singleTruck = vehicles.get(0);
            if (singleTruck.getRequiredCharging() <= target) {
                return Collections.singletonList(singleTruck.getId());
            }
            return Collections.emptyList();
        }

        vehicles.sort(Comparator.comparingDouble(Vehicle::getRequiredCharging));

        int left = 0;
        int right = vehicles.size() - 1;
        double closestSum = Double.NEGATIVE_INFINITY;
        int closestLeftIndex = -1;
        int closestRightIndex = -1;

        Integer singleClosestVehicleIndex = null;
        double closestSingleCharge = Double.NEGATIVE_INFINITY;

        // Two-pointer approach to find closest pair and single truck
        while (left < right) {
            double leftCharge = vehicles.get(left).getRequiredCharging();
            double rightCharge = vehicles.get(right).getRequiredCharging();
            double chargeSum = leftCharge + rightCharge;

            // Update closest pair if valid and better
            if (chargeSum <= target && chargeSum > closestSum) {
                closestSum = chargeSum;
                closestLeftIndex = left;
                closestRightIndex = right;
            }

            // Update closest single truck if valid and better
            if (leftCharge <= target && leftCharge > closestSingleCharge) {
                closestSingleCharge = leftCharge;
                singleClosestVehicleIndex = left;
            }
            if (rightCharge <= target && rightCharge > closestSingleCharge) {
                closestSingleCharge = rightCharge;
                singleClosestVehicleIndex = right;
            }

            // Move pointers based on the sum comparison
            if (chargeSum < target) {
                left++;
            } else {
                right--;
            }
        }

        // Decide whether to return the closest pair or single truck
        if (closestLeftIndex != -1 && closestRightIndex != -1 &&
                vehicles.get(closestLeftIndex).getRequiredCharging() + vehicles.get(closestRightIndex).getRequiredCharging() > closestSingleCharge) {
            return Arrays.asList(vehicles.get(closestLeftIndex).getId(), vehicles.get(closestRightIndex).getId());
        } else if (singleClosestVehicleIndex != null) {
            return Collections.singletonList(vehicles.get(singleClosestVehicleIndex).getId());
        }

        // No valid trucks found
        return Collections.emptyList();
    }
}
