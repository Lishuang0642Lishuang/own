//package com.example.own.common.limit;
//
//import com.example.own.common.enums.LimitTimeUnitEnum;
//import com.example.own.common.model.Statistic;
//import com.example.own.common.model.WindowWrap;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.concurrent.atomic.AtomicReferenceArray;
//import java.util.concurrent.locks.ReentrantLock;
//
//@Slf4j
//@AllArgsConstructor
//@Data
//public class CalculateLimitUnit {
//
//    private Integer threshold;
//
//    /**
//     * @see  LimitTimeUnitEnum
//     */
//    private Integer unit;
//
//    private Integer unitTime;
//
//    private final ReentrantLock updateLock = new ReentrantLock();
//
//    private final AtomicReferenceArray<WindowWrap<Object>> arrayRef = new AtomicReferenceArray<>(2);
//
//    public boolean addPassRequest() {
//        long now = System.currentTimeMillis();
//        long timeId = now / 500;
//        // Calculate current index so we can map the timestamp to the leap array.
//        int idx = (int)(timeId % 2);
//        WindowWrap<Object> windowWrap = choose(idx, now);
//        addPass(false, now, windowWrap);
//
//        long qps = values(now);
//        if (qps >= threshold) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    public boolean isWindowDeprecated(long time, WindowWrap<Object> windowWrap) {
//        return time - windowWrap.windowStart() > 1000;
//    }
//
//    public long values(long currentTime) {
//        long result = 0;
//        for (int i = 0; i < 2; i++) {
//            WindowWrap<Object> windowWrap = arrayRef.get(i);
//            if (windowWrap == null || isWindowDeprecated(currentTime, windowWrap)) {
//                continue;
//            }
//            Statistic statistic = (Statistic)windowWrap.value();
//            result += statistic.getHits().sum();
//        }
//        return result;
//    }
//
//    private long addPass(boolean innerCall, long currentTimeMillis, WindowWrap<Object> windowWrap) {
//        long now = currentTimeMillis;
//        Statistic val = (Statistic) windowWrap.value();
//        if (!innerCall) {
//            val.getHits().increment();
//        }
//        return now;
//    }
//
//    public WindowWrap<Object> choose(int idx, long currentTime) {
//        while (true) {
//            long slotWindowStart = currentTime - currentTime % 500;
//            WindowWrap<Object> old = arrayRef.get(idx);
//            if (old == null) {
//                WindowWrap<Object> window = new WindowWrap<Object>(500, slotWindowStart, new Statistic());
//                if (arrayRef.compareAndSet(idx, null, window)) {
//                    return window;
//                } else {
//                    Thread.yield();
//                }
//            } else if (slotWindowStart == old.windowStart()) {
//                return old;
//            } else if (slotWindowStart > old.windowStart()) {
//                if (updateLock.tryLock()) {
//                    try {
//                        // Successfully get the update lock, now we reset the bucket.
//                        return resetWindowTo(old, slotWindowStart);
//                    } finally {
//                        updateLock.unlock();
//                    }
//                } else {
//                    // Contention failed, the thread will yield its time slice to wait for bucket available.
//                    Thread.yield();
//                }
//            } else if (slotWindowStart < old.windowStart()) {
//                // Should not go through here, as the provided time is already behind.
//                return new WindowWrap<Object>(500, slotWindowStart, new Statistic());
//            }
//        }
//    }
//
//    public WindowWrap<Object> resetWindowTo(WindowWrap windowWrap, long startTime) {
//        Statistic val = (Statistic) windowWrap.value();
//        val.getHits().reset();
//        windowWrap.resetTo(startTime);
//        return windowWrap;
//    }
//
//}
