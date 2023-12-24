package com.example.own.api;

import org.apache.commons.lang3.RandomUtils;

public class TestMain2 {

    public static void main(String[] args) {

        for (int i = 0; i < 1000; i++) {
            System.out.println(RandomUtils.nextInt(0,2));
        }
    }
}
