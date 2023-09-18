package com.example.own.common.utils;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.stream.Collectors;

public class LengthUtil {


    /**
     * 获取对象字节长度的大小
     * @param object
     * @return
     */
    public static long getLength(Object object) {
        return new Gson().toJson(object).getBytes().length;
    }


    public static void main(String[] args) {

        String m = "AVPHwEsk2E48lMBnWleZFKKnIbJQXL6tEgdrTh1meU4bEGLS5TxgVJVBAc9oSbiZQopmXIyQkrCUobLRmz4SqOst_Sc6jg";
        System.out.println(m.length());



        String a = "lkj,po";
        String[] array = a.split(",");

      String returnValue =  Arrays.stream(array).map(item -> {
            return item + "1";
        }).collect(Collectors.joining(","));

        System.out.println(returnValue);
    }
}
