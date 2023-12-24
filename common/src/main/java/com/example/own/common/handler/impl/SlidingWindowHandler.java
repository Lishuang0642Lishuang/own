package com.example.own.common.handler.impl;

import com.example.own.common.handler.FlowLimitAbstractHandler;
import com.example.own.common.limit.SlidingWindowCalculateUnit;
import com.example.own.common.model.LimitDetail;
import com.example.own.common.model.WindowWrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @desc: 滑动窗口的处理类
 * @author: link.li 
 * @date: 2023/10/29
 * 
 */
@Slf4j
@Component
public class SlidingWindowHandler extends FlowLimitAbstractHandler {

    // key: group + identifier   value: CalculateLimitUnit
    private final Map<String, SlidingWindowCalculateUnit> limitUnitMap = new ConcurrentHashMap<>();


    private final ReentrantLock updateLock = new ReentrantLock();

    private AtomicReferenceArray<WindowWrap<Object>> arrayRef;


    @Override
    public Boolean addRequest(LimitDetail limitDetail) {

        Integer finalSlotNum = getFinalSlotNum(limitDetail);
        Integer slotTime = getSlotTime(limitDetail);


        SlidingWindowCalculateUnit calculateUnit = limitUnitMap.computeIfAbsent(limitDetail.getGroup() + "#" + limitDetail.getIdentifier(),
                key -> new SlidingWindowCalculateUnit(limitDetail.getMaxTimes(), finalSlotNum, slotTime, new AtomicReferenceArray<>(finalSlotNum)));

        boolean result = calculateUnit.addPassRequest();

        //todo ls 如果result是false的话，执行什么拒绝策略，这里需要开个口子






        return null;
    }

}
