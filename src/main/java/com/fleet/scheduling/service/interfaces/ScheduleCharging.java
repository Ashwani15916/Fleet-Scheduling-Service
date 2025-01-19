package com.fleet.scheduling.service.interfaces;

import com.fleet.scheduling.request.ScheduleRequest;

import java.util.List;
import java.util.Map;

public interface ScheduleCharging {

    Map<Integer, List<Integer>> scheduleCharging(ScheduleRequest scheduleRequest);
}
