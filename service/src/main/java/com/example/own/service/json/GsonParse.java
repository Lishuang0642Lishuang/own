package com.example.own.service.json;

import com.google.gson.Gson;

import java.util.HashMap;

public class GsonParse {

    public static void main(String[] args) {

        String jsonString = "{\"name\":\"BeJson\",\"url\":\"http://www.bejson.com\",\"page\":88,\"isNonProfit\":true}";

        HashMap<String, String> map = new Gson().fromJson(jsonString, HashMap.class);
        System.out.println(map);
    }
}
