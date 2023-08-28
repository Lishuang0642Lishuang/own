package com.example.own.common.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @desc: 自建的锁
 * @author: 英布
 * @date: 2023/8/24
 *
 */
public class SpinLock {

    private AtomicBoolean spinLock = new AtomicBoolean(true);

    public SpinLock() {
    }

    public void lock() {
        boolean flag;
        do {
            flag = this.spinLock.compareAndSet(true, false);
        } while(!flag);

    }

    public void unlock() {
        this.spinLock.compareAndSet(false, true);
    }
}
