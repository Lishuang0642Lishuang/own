package com.example.own.api.controller.middleware;


import com.example.own.core.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


/**
 * @desc: 获取apollo的配置
 * @author: 英布
 * @date: 2022/11/19 9:06 下午
 */

@Slf4j
@Controller
public class RedisController {


    @Resource
    RedisClient redisClient;

    @ResponseBody
    @GetMapping("/getCache")
    public String getCache(String key) {

        return redisClient.getValue(key).toString();
    }

    @ResponseBody
    @PostMapping("/setCache")
    public void setCache(String key, String value) {

        redisClient.setValue(key, value);
    }
}
