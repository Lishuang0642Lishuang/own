package com.example.own.service.calculateCase;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum ApplicationEnum {



//    IOT_SERVICE("iot", "iot-service"),
    APP("app", "ecoflow-app\\app-service\\target\\classes\\","ecoflow-app\\app-service\\target\\classes\\com\\ecoflow\\app\\controller"),
    ;


    private final String name;

    private final String classLoaderAddress;


    private final String controllerAddress;

    ApplicationEnum(String name, String classLoaderAddress, String controllerAddress) {
        this.name = name;
        this.classLoaderAddress = classLoaderAddress;
        this.controllerAddress = controllerAddress;
    }

    public final static Map<String, String> nameAddressMap = new HashMap<>();

    public final static Map<String, ApplicationEnum> nameEnumMap = new HashMap<>();


    static {
        Arrays.stream(ApplicationEnum.values()).forEach(item -> nameAddressMap.put(item.getName(), item.getControllerAddress()));
        Arrays.stream(ApplicationEnum.values()).forEach(item -> nameEnumMap.put(item.getName(), item));
    }
}
