package com.example.own.service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaConsumer {


    @KafkaListener(topics = {"testKafka"})
    public void listen(ConsumerRecord<?, ?> record) {

        Object object = record.value();

        log.info("get kafka value:{}",object);
        log.info("recode:{}", record);
    }


}
