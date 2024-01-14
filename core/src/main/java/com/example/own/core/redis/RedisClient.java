package com.example.own.core.redis;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

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

    public void setValue(String key, Object value, Long timeout, TimeUnit timeUnit) {

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    public Object getValue(String key) {
       return redisTemplate.opsForValue().get(key);
    }

    public void setSet(String key, Object value) {

        redisTemplate.opsForSet().add(key, value);

    }
}
