package com.example.own.common.vo;


import java.io.Serializable;

/**
 * @Author Banchao
 * @Date 2022/8/19 17:47
 */

public class TimeZoneIdDifferTimeVO implements Serializable {


    private Integer differHour;

    private Integer differMinute;

    public Integer getDifferHour() {
        return differHour;
    }

    public void setDifferHour(Integer differHour) {
        this.differHour = differHour;
    }

    public Integer getDifferMinute() {
        return differMinute;
    }

    public void setDifferMinute(Integer differMinute) {
        this.differMinute = differMinute;
    }

    @Override
    public String toString() {
        return "TimeZoneIdDifferTimeVO{" +
                "differHour=" + differHour +
                ", differMinute=" + differMinute +
                '}';
    }
}
