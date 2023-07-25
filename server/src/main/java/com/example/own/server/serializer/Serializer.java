package com.example.own.server.serializer;

import com.example.own.server.serializer.impl.JSONSerializer;

public interface Serializer {

    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();


    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制数据
     * @param object
     * @return
     */
    byte[] serialize(Object object);


    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
