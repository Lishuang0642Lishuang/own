package com.example.own.common.annotation;

import com.example.own.common.enums.LimitTimeUnitEnum;
import com.example.own.common.enums.LimitTypeEnum;

import java.lang.annotation.*;

/**
 * @desc: 限流的注解，当前限流只能适用于单pod的限流，没有涉及到IO,性能较好
 * @author: link.li 
 * @date: 2023/10/29
 * 
 */
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlowLimit {


    /**
     * @see LimitTypeEnum
     * 默认使用   滑动窗口
     * @return
     */
    String limitType() default "1";

    /**
     * 分组类型
     * @return
     */
    String group();

    /**
     * 唯一标识符，和group组合成唯一值，用于标识限流的key
     */
    String identifier();

    /**
     * @see LimitTimeUnitEnum
     * 时间类型，默认为秒*
     * @return
     */
    int unit() default 2;

    /**
     * 默认单位时长为 1 ，即:  默认1s
     * @return
     */
    int unitTime() default 1;

    /**
     * 单位时间内最大的次数*
     * @return
     */
    int maxTimes();
}
