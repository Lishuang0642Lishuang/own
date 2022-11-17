package com.example.own.service.time;

import com.example.own.service.time.dto.ScheduleDTO;

public interface ITimeZoneService {


    void parseTime(ScheduleDTO scheduleDTO, String destTimeZone);
}
