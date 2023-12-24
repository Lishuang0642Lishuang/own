package com.example.own.common.limit;

import com.example.own.common.model.LimitDetail;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;

@Slf4j
@Component
public class RollEachLimit {

    public static final Map<String, Map<String, Deque<LocalDateTime>>> WINDOW_TIMESTAMP_LIMITER_MAP = Maps.newConcurrentMap();

    public static Deque<LocalDateTime> dateTimeDeque;

    public static Map<String, Deque<LocalDateTime>> stringDequeMap;

    static {
        stringDequeMap = WINDOW_TIMESTAMP_LIMITER_MAP
                .computeIfAbsent("R601FEB5ZF4P0006", k -> {
                    Map<String, Deque<LocalDateTime>> dequeMap =  Maps.newConcurrentMap();
                    dequeMap.put("test", new ConcurrentLinkedDeque<>());
                    for (int i=0;i<100;i++) {
                        dequeMap.put("test"+i, new ConcurrentLinkedDeque<>());
                    }
                    return dequeMap;
                });
        dateTimeDeque = stringDequeMap.get("test");
    }

    public void windowLimitMethod(LimitDetail limitDetail, String requestHost, String groupName) {
        long startt = System.nanoTime();
        stringDequeMap.get("test12F4D5E6");
        long endd = System.nanoTime();
        log.info("map elapse {}", (endd - startt));
//        Map<String, Deque<LocalDateTime>> stringDequeMap = WINDOW_TIMESTAMP_LIMITER_MAP
//                .computeIfAbsent(requestHost, k -> {
//                    Map<String, Deque<LocalDateTime>> dequeMap =  Maps.newConcurrentMap();
//                    dequeMap.put(groupName, new ConcurrentLinkedDeque<>());
//                    return dequeMap;
//                });

        // 获取滑动窗口的时间窗时间类型,默认为毫秒
//        TemporalUnit temporalUnit;
//        switch (limitDetail.getUnit()) {
//            case "second":
//                temporalUnit = ChronoUnit.SECONDS;
//                break;
//            case "minute":
//                temporalUnit = ChronoUnit.MINUTES;
//                break;
//            default:temporalUnit = ChronoUnit.MILLIS;
//        }

        // 丢弃超出滑动窗口的失效数据

        long start = System.nanoTime();
        while (!dateTimeDeque.isEmpty() && LocalDateTime.now().minus(1, ChronoUnit.SECONDS).isAfter(dateTimeDeque.peekFirst())) {
            dateTimeDeque.pollFirst();
        }

        // 超出最大请求次数报出异常
        if (dateTimeDeque.size() > limitDetail.getMaxTimes()) {
            log.info("exception ");
            long end = System.nanoTime();
            log.info("elapse nano {}", (end - start));
            return;
        }

        // 加入当前请求
        dateTimeDeque.push(LocalDateTime.now());
        long end = System.nanoTime();
        log.info("elapse nano {}", (end - start));
    }
}
