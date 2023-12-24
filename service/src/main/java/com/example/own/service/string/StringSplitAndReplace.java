package com.example.own.service.string;

import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;

public class StringSplitAndReplace {


    public static void main(String[] args) {


        String topic = "/app/{productType}/{sn}/thing/property/set";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("productType", "81");
        jsonObject.addProperty("sn", "snsnsnsnnsns");
        topic = parseTopic(topic, jsonObject);
        System.out.println(topic);

    }




    public static String parseTopic(String topic, JsonObject paramJson) {

        String[] strArray = topic.split("/");


        for (String item : strArray) {
            if (StringUtils.startsWith(item, "{") && StringUtils.endsWith(item, "}")) {
                String param = item.substring(1, item.length() - 1);
                String paramValue = paramJson.get(param).getAsString();
                topic = topic.replace(item, paramValue);
            }
        }
        return topic;
    }
}
