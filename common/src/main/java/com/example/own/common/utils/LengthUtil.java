package com.example.own.common.utils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LengthUtil {


    /**
     * 获取对象字节长度
     * @param object
     * @return
     */
    public static long getLength(Object object) {
        return new Gson().toJson(object).getBytes().length;
    }


    public static void main(String[] args) {
        String a = "lkj,po";
        String[] array = a.split(",");

      String returnValue =  Arrays.stream(array).map(item -> {
            return item + "1";
        }).collect(Collectors.joining(","));

        System.out.println(returnValue);
    }
}
