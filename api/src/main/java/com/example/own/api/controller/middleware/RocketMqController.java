package com.example.own.api.controller.middleware;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
public class RocketMqController {


    @Resource
    private RocketMQTemplate rocketMQTemplate;


    @ResponseBody
    @GetMapping("/rocketmq")
    public String getCache(String key) {

        SendResult result = rocketMQTemplate.syncSend("sdf", "sdf");
        log.info("推rocketmq result:{}", new Gson().toJson(result));
        log.info(System.getProperty("rocketmq.broker.diskSpaceCleanForciblyRatio", "0.85"));
        return "";
    }

}
