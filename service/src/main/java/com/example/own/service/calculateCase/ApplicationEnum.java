package com.example.own.service.calculateCase;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum ApplicationEnum {



    USER("user", "ecoflow-user\\ecoflow-service-user\\target\\classes\\", "ecoflow-user\\ecoflow-service-user\\target\\classes\\com\\ecoflow\\service\\user\\controller"),
    IOT_SMART_VOICE("iot-smart-voice", "ecoflow-iot-smart-voice\\smart-voice-service\\target\\classes\\","ecoflow-iot-smart-voice\\smart-voice-service\\target\\classes\\com\\ecoflow\\iot\\smartvoice\\controller"),
    IOT_SCENE("iot-scene", "ecoflow-iot-scene\\ecoflow-iot-scene-service\\target\\classes\\", "ecoflow-iot-scene\\ecoflow-iot-scene-service\\target\\classes\\com\\ecoflow\\iot\\scene\\controller"),
    IOT_OTA("iot-ota", "ecoflow-iot-ota\\ota-service\\target\\classes\\", "ecoflow-iot-ota\\ota-service\\target\\classes\\com\\ecoflow\\iot\\ota\\controller"),
    IOT_OPEN("iot-open", "ecoflow-iot-open\\open-service\\target\\classes\\", "ecoflow-iot-open\\open-service\\target\\classes\\com\\ecoflow\\iot\\open\\controller"),
    IOT_DEVICE("iot-devices", "ecoflow-iot-devices\\devices-service\\target\\classes\\","ecoflow-iot-devices\\devices-service\\target\\classes\\com\\ecoflow\\iot\\devices\\controller"),
    GENERAL("general", "ecoflow-general\\general-service\\target\\classes\\","ecoflow-general\\general-service\\target\\classes\\com\\ecoflow\\general\\controller"),
    EXPORT("export","ecoflow-export\\export-service\\target\\classes\\","ecoflow-export\\export-service\\target\\classes\\com\\ecoflow\\export\\controller"),
    DATA("data-service", "ecoflow-data\\data-service\\target\\classes\\","ecoflow-data\\data-service\\target\\classes\\com\\ecoflow\\data\\controller"),
    CMS("cms", "ecoflow-cms\\cms-service\\target\\classes\\","ecoflow-cms\\cms-service\\target\\classes\\com\\ecoflow\\cms\\controller"),
    BATTERY("battery-service", "ecoflow-battery\\battery-service\\target\\classes\\","ecoflow-battery\\battery-service\\target\\classes\\com\\ecoflow\\battery\\controller"),
    AUTH("auth","ecoflow-auth\\target\\classes\\","ecoflow-auth\\target\\classes\\com\\ecoflow\\auth\\controller"),
    IOT_AUTH("iot-auth", "ecoflow-iot-auth\\auth-service\\target\\classes\\","ecoflow-iot-auth\\auth-service\\target\\classes\\com\\ecoflow\\iot\\auth\\controller"),
    IOT_SERVICE("iot-service","ecoflow-iot\\iot-service\\iot-service\\target\\classes\\", "ecoflow-iot\\iot-service\\iot-service\\target\\classes\\com\\ecoflow\\iot\\controller"),
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
