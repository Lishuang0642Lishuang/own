package com.example.own.service.time.impl;

import com.example.own.common.utils.DateTimeUtils;
import com.example.own.service.time.ITimeZoneService;
import com.example.own.service.time.dto.ScheduleDTO;
import org.springframework.stereotype.Service;

@Service("timeZoneService")
public class TimeZoneServiceImpl implements ITimeZoneService {
    @Override
    public void parseTime(ScheduleDTO scheduleDTO, String destTimeZone) {
        String loop = scheduleDTO.getLoops();

        Integer differMinute = DateTimeUtils.getDifferWithTimeZoneIdV2(scheduleDTO.getTimeZoneId(), destTimeZone);

        Integer onMinute = DateTimeUtils.parseTimeToMinute(scheduleDTO.getOn());
        Integer offMinute = DateTimeUtils.parseTimeToMinute(scheduleDTO.getOff());

        Integer destOnMinute = onMinute - differMinute;
        Integer destOffMinute = offMinute - differMinute;

        scheduleDTO.setOn(DateTimeUtils.parseMinuteToTime((destOnMinute + DateTimeUtils.DAY_MINUTE) % DateTimeUtils.DAY_MINUTE));
        scheduleDTO.setOff(DateTimeUtils.parseMinuteToTime((destOffMinute + DateTimeUtils.DAY_MINUTE) % DateTimeUtils.DAY_MINUTE));
        scheduleDTO.setTimeZoneId(destTimeZone);

        if (destOnMinute < 0) {
            String newLoop = (loop + loop).substring(1,8);
            scheduleDTO.setLoops(newLoop);
        } else if (destOnMinute > DateTimeUtils.DAY_MINUTE) {
            String newLoop = (loop + loop).substring(6,13);
            scheduleDTO.setLoops(newLoop);
        }
    }
}
