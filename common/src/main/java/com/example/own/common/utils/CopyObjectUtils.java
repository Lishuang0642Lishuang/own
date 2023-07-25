package com.example.own.common.utils;


import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;



/**
 * @desc:
 * 1、CopyObjectUtils需要用到get set方法，不然的话，就转化不过来
 * 2、需要注意类中包含的子类的问题，有时候转化不过来
 * @author: 英布
 * @date: 2022/11/8 11:33 下午
 *
 */

public class CopyObjectUtils {

    private CopyObjectUtils() {
    }

    /**
     * 中间，字段
     */
    private static final String MIDDLE_SSRING = "_";

    /**
     * 默认字段工厂（适用于，属性名称，都相同情况）
     */
    private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();

    /**
     * 默认字段实例
     */
    private static final MapperFacade MAPPER_FACADE = MAPPER_FACTORY.getMapperFacade();

    /**
     * 默认字段实例集合
     */
    private static Map<String, MapperFacade> CACHE_MAPPER_FACADE_MAP = new ConcurrentHashMap<>();

    /**
     * 映射实体（默认字段映射，适用于，属性名称，都相同情况）
     *
     * @param sourceData       数据（对象）
     * @param destinationClass 映射类对象
     * @return 映射类对象
     */
    public static <S, D> D copyAtoB(S sourceData, Class<D> destinationClass) {
        if (null == sourceData) {
            return null;
        }
        return MAPPER_FACADE.map(sourceData, destinationClass);
    }

    /**
     * 映射集合（默认字段映射，适用于，属性名称，都相同情况）
     *
     * @param sourceDataList   数据（集合）
     * @param destinationClass 映射类对象
     * @return 映射类对象
     */
    public static <D, S> List<D> copyAlistToBlist(Iterable<S> sourceDataList, Class<D> destinationClass) {
        if (null == sourceDataList) {
            return Collections.emptyList();
        }
        return MAPPER_FACADE.mapAsList(sourceDataList, destinationClass);
    }

    /**
     * 映射实体（自定义配置，支持不同属性名称映射）
     *
     * @param sourceData       数据（对象）
     * @param destinationClass 映射类对象
     * @param filedNameMapping 自定义配置 Map<sourceClass 的属性name, destinationClass 的属性Name>
     * @return 映射类对象
     */
    public static <S, D> D copyAtoB(S sourceData, Class<D> destinationClass, Map<String, String> filedNameMapping) {
        if (null == sourceData) {
            return null;
        }
        MapperFacade mapperFacade = getMapperFacade(sourceData.getClass(), destinationClass, filedNameMapping);
        return mapperFacade.map(sourceData, destinationClass);
    }

    /**
     * 映射集合（自定义 mapping 配置，支持不同属性名称映射）
     *
     * @param sourceDataList   数据（集合）
     * @param destinationClass 映射类
     * @param filedNameMapping 自定义配置 Map<sourceClass 的属性name, destinationClass 的属性Name>
     * @return 映射类对象
     */
    public static <D, S> List<D> copyAlistToBlist(Iterable<S> sourceDataList, Class<D> destinationClass,
                                                  Map<String, String> filedNameMapping) {

        // null 判断
        if (null == sourceDataList) {
            return Collections.emptyList();
        }

        // 判断，sourceDataList 是否为空集合
        boolean hasNext = sourceDataList.iterator().hasNext();
        if (!hasNext) {
            return Collections.emptyList();
        }

        Class<?> sourceClass = null;
        // 遍历，获取，第一个不为null的元素
        Iterator<S> itr = sourceDataList.iterator();
        while (itr.hasNext()) {
            S first = itr.next();

            // 找到，不为null的元素，提取class
            if (first != null) {
                sourceClass = first.getClass();
                break;
            }
        }

        // 如果，sourceClass == null，说明sourceDataList里面，全部元素，都为null，返回空集合
        if (sourceClass == null) {
            return Collections.emptyList();
        }

        // 通过t.getClass()，获取，自定义 MapperFacade 映射
        MapperFacade mapperFacade = getMapperFacade(sourceClass, destinationClass, filedNameMapping);

        // 数据转换
        return mapperFacade.mapAsList(sourceDataList, destinationClass);
    }

    /**
     * 获取，自定义 MapperFacade 映射
     *
     * @param sourceClass      映射类
     * @param destinationClass 数据映射类
     * @param filedNameMapping 自定义配置 Map<sourceClass 的属性name, destinationClass 的属性Name>
     * @return 映射类对象
     */
    private static <D, S> MapperFacade getMapperFacade(Class<S> sourceClass, Class<D> destinationClass,
                                                       Map<String, String> filedNameMapping) {

        // 组装mapKey，通过本地 CACHE_MAPPER_FACADE_MAP 映射 MapperFacade
        String mapKey = destinationClass.getCanonicalName() + MIDDLE_SSRING + sourceClass.getCanonicalName();
        MapperFacade mapperFacade = CACHE_MAPPER_FACADE_MAP.get(mapKey);

        // 如果为null，就初始化一下
        if (Objects.isNull(mapperFacade)) {
            MapperFactory factory = new DefaultMapperFactory.Builder().build();
            ClassMapBuilder classMapBuilder = factory.classMap(sourceClass, destinationClass);
            filedNameMapping.forEach(classMapBuilder::field);
            classMapBuilder.byDefault().register();
            mapperFacade = factory.getMapperFacade();
            CACHE_MAPPER_FACADE_MAP.put(mapKey, mapperFacade);
        }
        return mapperFacade;
    }


    public static void main(String[] args) {

        Bean2 bean2 = new Bean2();
        bean2.setKey(1L);
        Bean1 bean1 = CopyObjectUtils.copyAtoB(bean2, Bean1.class);
        System.out.println(bean1);
    }

}

