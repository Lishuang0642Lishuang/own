package com.example.own.api.controller.middleware;

import com.example.own.service.mongo.IMongoAggregateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
public class AggregateController {


    @Resource
    IMongoAggregateService mongoAggregateService;


    @ResponseBody
    @RequestMapping("/aggregate")
    public void aggregate() {
        mongoAggregateService.aggregateDuplication();
    }

    @ResponseBody
    @RequestMapping("/aggregateOnlyCode")
    public void aggregateOnlyCode() {
        mongoAggregateService.aggregateOnlyCode();
    }
}
