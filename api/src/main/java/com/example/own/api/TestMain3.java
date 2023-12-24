package com.example.own.api;

import com.google.gson.Gson;

public class TestMain3 {


    public static void main(String[] args) {
        String json = "{\"error\":{\"code\":429,\"message\":\"Rate limited for the GetDevice API for the user.\",\"status\":\"RESOURCE_EXHAUSTED\"}}";

        GoogleNestDeviceResponse googleNestDeviceResponse = new Gson().fromJson(json, GoogleNestDeviceResponse.class);

        System.out.println("sdf");
    }
}
