package com.example.own.core.batch;

import com.example.own.common.lock.SpinLock;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc: bean的路由
 * @author: 英布
 * @date: 2023/8/24
 * 
 */
@Component
public class BeanRoute {

    private static final int cycleSize = 20;

    private Map<String, SpinLock> beanNameAndSpinLockMap = new ConcurrentHashMap<>(16);

    private Map<String, AtomicInteger> beanNameAndAtomicIntegerMap = new ConcurrentHashMap<>(16);

    private Map<String, Long> beanNameAndCurrentSpanMap = new ConcurrentHashMap<>(16);


    public SpinLock getSpinLock(String beanName) {

        SpinLock spinLock = beanNameAndSpinLockMap.get(beanName);
        if (ObjectUtils.isEmpty(spinLock)) {
            synchronized (this) {
                if (ObjectUtils.isEmpty(spinLock)) {
                    spinLock = new SpinLock();
                    beanNameAndSpinLockMap.put(beanName, spinLock);
                }
            }
        }
        return spinLock;
    }


    public AtomicInteger getAtomicInteger(String beanName) {

        AtomicInteger atomicInteger = beanNameAndAtomicIntegerMap.get(beanName);
        if (ObjectUtils.isEmpty(atomicInteger)) {
            synchronized (this) {
                if (ObjectUtils.isEmpty(atomicInteger)) {
                    atomicInteger = new AtomicInteger(0);
                    beanNameAndAtomicIntegerMap.put(beanName, atomicInteger);
                }
            }
        }
        return atomicInteger;
    }


    public Long getCurrentSpan(String beanName) {
        Long currentSpan = beanNameAndCurrentSpanMap.get(beanName);

        if (ObjectUtils.isEmpty(currentSpan)) {
            synchronized (this) {
                if (ObjectUtils.isEmpty(currentSpan)) {
                    currentSpan = System.currentTimeMillis();
                    beanNameAndCurrentSpanMap.put(beanName, currentSpan);
                }
            }
        }
        return currentSpan;
    }

    public void setCurrentSpan(String beanName, Long currentSpan) {
        beanNameAndCurrentSpanMap.put(beanName, currentSpan);
    }

    public int getCycleSize() {
        return cycleSize;
    }
}
