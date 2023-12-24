package com.example.own.service.string;

public class StringSplit {


    public static void main(String[] args) {
        String m = "sdm.devices.types.THERMOSTAT";
        String[] typeArray = m.split("[.]");
        String finalType = typeArray[typeArray.length -1];
        System.out.println(finalType);
    }
}
