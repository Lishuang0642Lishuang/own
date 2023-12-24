package com.example.own.common.config;

import com.example.own.common.model.LimitDetail;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流具体注解
 * @author osborn
 */
@Data
@Component
@ConfigurationProperties(prefix = "flowlimit")
public class FlowLimitProperties {

    /**
     * 限流组名称,用以分组限流,注解提供默认,该属性可自行维护
     * 限流策略为IP下,对应分组分开进行限流统计,如果不需要分组则使用默认即可
     * @return 限流组名称
     */
    Map<String, LimitDetail> group = new HashMap<>();

    /**
     * 限流策略类型,策略类型同一Group下应当维护同一种
     * DEFAULT 走默认限流策略,不考虑黑白名单参数
     * LimitType.WHITE_LIST 只考虑白名单策略,非白名单的请求全部回绝
     * LimitType.BLACK_LIST 只考虑黑名单策略,非黑名单请求不做限流措施
     * LimitType.DEFAULT_WITH_WHITE_LIST 在默认限流策略的基础上,白名单内的IP不做限流
     * LimitType.DEFAULT_WITH_BLACK_LIST 在默认限流策略的基础上,直接403黑名单
     * LimitType.DEFAULT_WITH_WHITE_AND_BLACK_LIST 在默认限流策略的基础上,直接403黑名单,再让白名单内的IP直接通行
     * @return 限流策略类型
     */


}

