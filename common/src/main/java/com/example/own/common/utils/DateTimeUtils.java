package com.example.own.common.utils;

import com.example.own.common.vo.ScheduleDTO;
import com.example.own.common.vo.TimeZoneIdDifferTimeVO;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * @desc: 处理时间相关的内容
 * @author: 英布
 * @date: 2022/11/17 1:01 上午
 *
 */

public class DateTimeUtils {


    public final static String YYYYMMDDHH = "yyyyMMddHH";

    public final static String YYYYMMDD = "yyyyMMdd";

    public final static String YYYY_MM_DD_HH_MM_SS_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public final static DateTimeFormatter YYYY_MM_DD_HH_MM_SS_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");


    //每天的分钟数
    public final static Integer DAY_MINUTE = 1440;



    /**
     * @desc: 对比两个时区，前面的减去后面的
     * 例如： 上海和东京  得到 -1 0
     *       上海和印度孟买 得到 2 30
     * @author: 英布
     * @date: 2022/11/17 1:18 上午
     *
     */

    public static TimeZoneIdDifferTimeVO getDifferWithTimeZoneId(String timeZoneId1, String timeZoneId2) {
        long currentTimeMillis = System.currentTimeMillis();
        DateTime timeZone1Dt =
                DateTime.parse(new DateTime(currentTimeMillis, DateTimeZone.forID(timeZoneId1)).toString(YYYY_MM_DD_HH_MM_SS_PATTERN),
                        YYYY_MM_DD_HH_MM_SS_FORMATTER);
        DateTime timeZone2Dt =
                DateTime.parse(new DateTime(currentTimeMillis, DateTimeZone.forID(timeZoneId2)).toString(YYYY_MM_DD_HH_MM_SS_PATTERN),
                        YYYY_MM_DD_HH_MM_SS_FORMATTER);

        long timeZone1TimeMillis = timeZone1Dt.getMillis();
        long timeZone2TimeMillis = timeZone2Dt.getMillis();


        long differTimeSeconds = (timeZone1TimeMillis - timeZone2TimeMillis) / 1000;

        TimeZoneIdDifferTimeVO vo = new TimeZoneIdDifferTimeVO();
        vo.setDifferHour((int) (differTimeSeconds / 3600));
        vo.setDifferMinute((int) (differTimeSeconds % 3600) / 60);

        return vo;
    }

    /**
     * @desc: 对比两个时区，前面的减去后面的
     * 例如： 上海和东京  得到 -60
     *       上海和印度孟买 得到 150
     * @author: 英布
     * @date: 2022/11/17 1:18 上午
     *
     */
    public static Integer getDifferWithTimeZoneIdV2(String timeZoneId1, String timeZoneId2) {
        long currentTimeMillis = System.currentTimeMillis();
        DateTime timeZone1Dt =
                DateTime.parse(new DateTime(currentTimeMillis, DateTimeZone.forID(timeZoneId1)).toString(YYYY_MM_DD_HH_MM_SS_PATTERN),
                        YYYY_MM_DD_HH_MM_SS_FORMATTER);
        DateTime timeZone2Dt =
                DateTime.parse(new DateTime(currentTimeMillis, DateTimeZone.forID(timeZoneId2)).toString(YYYY_MM_DD_HH_MM_SS_PATTERN),
                        YYYY_MM_DD_HH_MM_SS_FORMATTER);

        long timeZone1TimeMillis = timeZone1Dt.getMillis();
        long timeZone2TimeMillis = timeZone2Dt.getMillis();


        long differTimeSeconds = (timeZone1TimeMillis - timeZone2TimeMillis) / 1000;

        return Integer.parseInt(String.valueOf(differTimeSeconds/60));
    }


    public static void parseTime(ScheduleDTO scheduleDTO, String destTimeZone) {

        String loop = scheduleDTO.getLoops();

        Integer differMinute = getDifferWithTimeZoneIdV2(scheduleDTO.getTimeZoneId(), destTimeZone);

        Integer onMinute = parseTimeToMinute(scheduleDTO.getOn());
        Integer offMinute = parseTimeToMinute(scheduleDTO.getOff());

        Integer destOnMinute = onMinute - differMinute;
        Integer destOffMinute = offMinute - differMinute;

        scheduleDTO.setOn(parseMinuteToTime((destOnMinute + DAY_MINUTE) % DAY_MINUTE));
        scheduleDTO.setOff(parseMinuteToTime((destOffMinute + DAY_MINUTE) % DAY_MINUTE));
        scheduleDTO.setTimeZoneId(destTimeZone);

        if (destOnMinute < 0) {
            String newLoop = (loop + loop).substring(1,8);
            scheduleDTO.setLoops(newLoop);
        } else if (destOnMinute > DAY_MINUTE) {
            String newLoop = (loop + loop).substring(6,13);
            scheduleDTO.setLoops(newLoop);
        }
    }

    /**
     * 01:30 转化为 90 分钟
     * @param time
     * @return
     */
    public static Integer parseTimeToMinute(String time) {
        String[] array = time.split(":");
        return Integer.parseInt(array[0]) * 60 + Integer.parseInt(array[1]);
    }

    /**
     * 将分钟转化为时间  90转化为 01:30
     * @param totalMinute
     * @return
     */
    public static String parseMinuteToTime(Integer totalMinute) {
        Integer hour = totalMinute / 60;
        Integer minute = totalMinute % 60;

        return String.format("%02d", hour) + ":" + String.format("%02d", minute);

    }



    public static void main(String[] args) {


//        TimeZoneIdDifferTimeVO vo =  getDifferWithTimeZoneId("Asia/Shanghai", "Asia/Kolkata");
//        System.out.println(vo);
//
//        Integer integer = getDifferWithTimeZoneIdV2("Asia/Shanghai", "Asia/Kolkata");
//        System.out.println(integer);

        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setLoops("0011001");
        scheduleDTO.setOff("23:00");
        scheduleDTO.setOn("22:00");
        scheduleDTO.setTimeZoneId("Asia/Shanghai");


        System.out.println(scheduleDTO);
        parseTime(scheduleDTO, "Asia/Magadan");
        System.out.println(scheduleDTO);

    }
}
