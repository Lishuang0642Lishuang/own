package com.example.own.api.enumpackage;

public enum AppEnum {

    LIKE(1,"点个赞"),
    UNLIKE(2, "不喜欢"),


    ;

    private final Integer code;

    private final String desc;

    AppEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
