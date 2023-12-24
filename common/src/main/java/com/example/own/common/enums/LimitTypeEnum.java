package com.example.own.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 限流器类型
 * @author: link.li
 * @date: 2023/10/29
 *
 */
@Getter
public enum LimitTypeEnum {


    SLIDING_WINDOW("1", "slidingWindowsHandler", "滑动窗口"),

    ;

    private final String limitType;

    private final String handler;

    private final String desc;

    LimitTypeEnum(String limitType, String handler, String desc) {

        this.limitType = limitType;
        this.handler = handler;
        this.desc = desc;
    }


    public final static Map<String, LimitTypeEnum> typeHandlerMap = new HashMap<>();

    static {
        Arrays.stream(LimitTypeEnum.values()).forEach(item -> typeHandlerMap.put(item.getLimitType(), item));
    }

    public static LimitTypeEnum getByLimitType(String limitType) {
        return typeHandlerMap.getOrDefault(limitType, SLIDING_WINDOW);

    }
}
