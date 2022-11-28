package com.example.own.core.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class KafkaClient {


    @Resource
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessage(String topic, Object value) {
        kafkaTemplate.send(topic, value);
    }
}
