package com.example.own.core.snowflake;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SnowFlakeBusiness {

    DEFAULT("default", "默认"),
    ;

    private final String code;
    private final String desc;


}
