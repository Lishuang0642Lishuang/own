package com.example.own.service.calculate;

public class NumCalculate {

    public static void main(String[] args) {

        Long time = System.currentTimeMillis();

        Long result1 = time/500;
        Long result2 = time%500;

        System.out.println(time);
        System.out.println(result1);
        System.out.println(result2);

    }
}
