package com.example.own.common.limit;

import com.example.own.common.model.Statistic;
import com.example.own.common.model.WindowWrap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
@Data
public class SlidingWindowCalculateUnit {

    /**
     * 设置的最大次数
     */
    private Integer maxTimes;

    /**
     * 槽的数量
     */
    private Integer slotNum;

    /**
     * 槽的持续时间
     */
    private Integer slotTime;

    /**
     * 数组的数量
     */
    private AtomicReferenceArray<WindowWrap<Object>> arrayRef;

    private final ReentrantLock updateLock = new ReentrantLock();

    public SlidingWindowCalculateUnit(Integer maxTimes, Integer slotNum, Integer slotTime, AtomicReferenceArray<WindowWrap<Object>> arrayRef) {
        this.maxTimes = maxTimes;
        this.slotNum = slotNum;
        this.slotTime = slotTime;
        this.arrayRef = arrayRef;
    }

    public boolean addPassRequest() {

        long now = System.currentTimeMillis();
        long timeId = now / slotTime;
        // Calculate current index so we can map the timestamp to the leap array.
        int idx = (int)(timeId % slotNum);
        WindowWrap<Object> windowWrap = choose(idx, now);
        addPass(false, now, windowWrap);

        long qps = values(now);
        if (qps >= maxTimes) {
            return false;
        } else {
            return true;
        }

    }

    public boolean isWindowDeprecated(long time, WindowWrap<Object> windowWrap) {
        return time - windowWrap.windowStart() > (long) slotNum * slotTime;
    }

    public long values(long currentTime) {
        long result = 0;
        for (int i = 0; i < slotNum; i++) {
            WindowWrap<Object> windowWrap = arrayRef.get(i);
            if (windowWrap == null || isWindowDeprecated(currentTime, windowWrap)) {
                continue;
            }
            Statistic statistic = (Statistic)windowWrap.value();
            result += statistic.getHits().sum();
        }
        return result;
    }

    private long addPass(boolean innerCall, long currentTimeMillis, WindowWrap<Object> windowWrap) {
        long now = currentTimeMillis;
        Statistic val = (Statistic) windowWrap.value();
        if (!innerCall) {
            val.getHits().increment();
        }
        return now;
    }

    public WindowWrap<Object> choose(int idx, long currentTime) {
        while (true) {
            long slotWindowStart = currentTime - currentTime % slotTime;
            WindowWrap<Object> old = arrayRef.get(idx);
            if (old == null) {
                WindowWrap<Object> window = new WindowWrap<Object>(500, slotWindowStart, new Statistic());
                if (arrayRef.compareAndSet(idx, null, window)) {
                    return window;
                } else {
                    Thread.yield();
                }
            } else if (slotWindowStart == old.windowStart()) {
                return old;
            } else if (slotWindowStart > old.windowStart()) {
                if (updateLock.tryLock()) {
                    try {
                        // Successfully get the update lock, now we reset the bucket.
                        return resetWindowTo(old, slotWindowStart);
                    } finally {
                        updateLock.unlock();
                    }
                } else {
                    // Contention failed, the thread will yield its time slice to wait for bucket available.
                    Thread.yield();
                }
            } else if (slotWindowStart < old.windowStart()) {
                // Should not go through here, as the provided time is already behind.
                return new WindowWrap<Object>(500, slotWindowStart, new Statistic());
            }
        }
    }

    public WindowWrap<Object> resetWindowTo(WindowWrap windowWrap, long startTime) {
        Statistic val = (Statistic) windowWrap.value();
        val.getHits().reset();
        windowWrap.resetTo(startTime);
        return windowWrap;
    }

}
