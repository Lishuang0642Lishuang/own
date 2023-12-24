package com.example.own.common.handler;

import com.example.own.common.enums.LimitTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @desc: Handler相关方法
 * @author: link.li
 * @date: 2023/10/29
 *
 */
@Slf4j
@Component
public class HandlerFactory {

    @Resource
    Map<String, FlowLimitHandler> flowLimitHandlerMap;

    /**
     * 根据limitType 拿handler, 默认返回 -> 滑动窗口的handler
     * @param limitType
     * @return
     */
    public FlowLimitHandler getHandlerByLimitType(String limitType) {

        LimitTypeEnum typeEnum = LimitTypeEnum.getByLimitType(limitType);

        return flowLimitHandlerMap.getOrDefault(typeEnum.getHandler(), flowLimitHandlerMap.get(LimitTypeEnum.SLIDING_WINDOW.getHandler()));
    }



}
