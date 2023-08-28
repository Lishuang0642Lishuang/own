package com.example.own.service.factorybean;


import org.springframework.beans.factory.FactoryBean;

/**
 * @desc: 类的增强
 * @author:英布
 * @date: 2022-12-11 16:50:37
 *
 */
public class BeanEnhance<T> implements FactoryBean<T> {
    @Override
    public T getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
