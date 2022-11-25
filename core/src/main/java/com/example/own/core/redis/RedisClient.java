package com.example.own.core.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @desc: redis的工具类
 * @author: 英布
 * @date: 2022/11/19 9:06 下午
 *
 */

@Component
public class RedisClient {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object getValue(String key) {
       return redisTemplate.opsForValue().get(key);
    }
}
