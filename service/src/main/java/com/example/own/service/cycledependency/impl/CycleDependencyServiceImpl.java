package com.example.own.service.cycledependency.impl;

import com.example.own.service.cycledependency.ICycleDependencyService;
import com.example.own.service.cycledependency.bean.ClassOne;
import com.example.own.service.cycledependency.bean.ClassTwo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;


/**
 * @desc: 循环依赖的问题，其实很简单，只是之前自己想的太复杂了，把自己给吓到了
 * @author:英布
 * @date: 2023-01-25 00:15:45
 *
 */
@Service("cycleDependencyService")
@Slf4j
public class CycleDependencyServiceImpl implements ICycleDependencyService {


    private static Map<String, Object> cacheMap = new HashMap<>();



    @Override
    public void cycleDependencyResolve() {


    }


    public static void main(String[] args) {
        Class[] classes = {ClassOne.class, ClassTwo.class};
        for (Class aClass : classes) {
            getBean(aClass);
        }

        System.out.println(getBean(ClassOne.class).getClassTwo() == getBean(ClassTwo.class));
        System.out.println(getBean(ClassTwo.class).getClassOne() == getBean(ClassOne.class));
    }

    @SneakyThrows
    private static <T> T getBean(Class<T> beanClass) {

        String beanName = beanClass.getSimpleName().toLowerCase();

        if(cacheMap.containsKey(beanName)) {
            return (T) cacheMap.get(beanName);
        }

        Object object = beanClass.newInstance();
        cacheMap.put(beanName, object);

        Field[] fields = object.getClass().getDeclaredFields();
        for(Field field: fields) {
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();
            String fieldBeanName = fieldClass.getSimpleName().toLowerCase();

            field.set(object, cacheMap.containsKey(fieldBeanName)? cacheMap.get(fieldBeanName) : getBean(fieldClass));
        }

        return (T) object;
    }
}
