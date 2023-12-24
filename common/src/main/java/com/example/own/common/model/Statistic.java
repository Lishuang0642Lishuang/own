package com.example.own.common.model;

import java.util.concurrent.atomic.LongAdder;

/**
 * @desc: 统计数量
 * @author: link.li
 * @date: 2023/10/31
 *
 */
public class Statistic {

    private final LongAdder hits = new LongAdder();

    public LongAdder getHits() {
        return hits;
    }
}
