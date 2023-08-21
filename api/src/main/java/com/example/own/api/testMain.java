package com.example.own.api;

import com.example.own.api.enumpackage.AppEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class testMain {


    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        Arrays.stream(AppEnum.values()).forEach(item -> map.put(item.name(), item.getDesc()));

        System.out.println(map);
    }
}
