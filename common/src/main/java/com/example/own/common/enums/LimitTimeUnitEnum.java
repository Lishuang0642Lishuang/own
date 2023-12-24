package com.example.own.common.enums;

import lombok.Getter;

/**
 * 限流的时间单位类型
 * @author van
 */
@Getter
public enum LimitTimeUnitEnum {
    /**
     * 毫秒
     */
//    MILLISECOND(1, "毫秒", 1),
    /**
     * 秒
     */
    SECOND(2, "秒", 2),
    /**
     * 分钟
     */
    MINUTE(3, "分",5),
    ;


    private final Integer code;

    private final String desc;

    private final Integer slotNum;

    LimitTimeUnitEnum(Integer code, String desc, Integer slotNum) {
        this.code = code;
        this.desc = desc;
        this.slotNum = slotNum;
    }
}

