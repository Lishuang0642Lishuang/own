package com.example.own.service.optional;


import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @desc 通过optional来进行判空
 * @author 英布
 * @date 2023/4/5 18:13
 */
public class OptionalService {


    public static void main(String[] args) throws Exception{



        Class<?> clazz2 = Class.forName("com.example.own.service.optional.ClassType");
        Method[] methods = clazz2.getMethods();

        for (Method method: methods) {
            Parameter[] parameters = method.getParameters();
            System.out.println("sdf");
        }






        Class<?> clazz = Class.forName("com.example.own.service.optional.OptionalService");
        Annotation[] annotations =  clazz.getDeclaredAnnotations();

        if(ArrayUtils.isEmpty(annotations)) {
            System.out.println("com.example.own.service.optional");
        }

        for (Annotation annotation: annotations) {
            System.out.println("sdfdd");
        }


        List<String> list = new ArrayList<>();

        List<String> list1 = null;

        Optional.ofNullable(list).ifPresent(item -> item.add("sdf"));
        System.out.println("sdf");
    }
}
