package com.example.own.api.controller.middleware;

import com.example.own.core.kafka.KafkaClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Slf4j
@Controller
public class KafkaController {

    @Resource
    KafkaClient kafkaClient;

    @ResponseBody
    @RequestMapping("/sendMessage")
    public void sendMessage(String topic, String value) {

        kafkaClient.sendMessage(topic, value);

    }
}
