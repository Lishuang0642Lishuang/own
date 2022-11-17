package com.example.own.service.time.dto;

public class ScheduleDTO {


    /**
     * 计划开始时间
     */
    private String on;

    /**
     * 计划结束时间
     */
    private String off;

    /**
     * 自定义模式计划的loops
     */
    private String loops;

    /**
     * 时区，例如 Asia/shanghai
     */
    private String timeZoneId;

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }

    public String getLoops() {
        return loops;
    }

    public void setLoops(String loops) {
        this.loops = loops;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    @Override
    public String toString() {
        return "ScheduleDTO{" +
                "on='" + on + '\'' +
                ", off='" + off + '\'' +
                ", loops='" + loops + '\'' +
                ", timeZoneId='" + timeZoneId + '\'' +
                '}';
    }
}
