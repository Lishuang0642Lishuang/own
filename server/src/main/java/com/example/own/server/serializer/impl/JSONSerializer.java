package com.example.own.server.serializer.impl;

import com.alibaba.fastjson.JSON;
import com.example.own.server.bean.SerializerAlgorithm;
import com.example.own.server.serializer.Serializer;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
