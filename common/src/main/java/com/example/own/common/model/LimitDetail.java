package com.example.own.common.model;

import com.example.own.common.enums.LimitTimeUnitEnum;
import lombok.Data;

@Data
public class LimitDetail {

    /**
     * 限流器类型,默认采用滑动窗口限流器,可配置为令牌桶模式
     * todo(van) 可用户自行实现限流器
     * @return 限流器类型
     */
    String limitType;

    /**
     * 分组*
     */
    String group;

    /**
     * 标识符*
     */
    String identifier;

    /**
     * 时间单位
     * @see LimitTimeUnitEnum
     */
    Integer unit;

    /**
     * 限流时长
     *
     */
    Integer unitTime;


    /**
     * 限流单位时间长度内的最多次数
     * @return 最多次数
     */
    Integer maxTimes;



    /**
     * 白名单,使用方式由 limitType() 确定,比如选择默认LimitType.DEFAULT时该参数配置无用
     * @return 白名单str
     */
    String[] whiteList;

    /**
     * 黑名单,使用方式由 limitType() 确定,比如选择默认LimitType.DEFAULT时该参数配置无用
     * @return 黑名单list
     */
    String[] blackList;
}
