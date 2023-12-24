package com.example.own.core.redis;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * @desc: 给任务设置定时时限，比如，任务最多执行XX分钟
 * @author: link.li
 * @date: 2023/11/15
 *
 */
@Slf4j
public class TimedScheduledExecutorPoolExecutor extends ScheduledThreadPoolExecutor {




    private volatile Long deadLine;

    public TimedScheduledExecutorPoolExecutor(int corePoolSize) {
        super(corePoolSize);
    }


    public void setDeadLine(Long deadLine) {
        this.deadLine = deadLine;
    }






    protected <V> RunnableScheduledFuture<V> decorateTask(
            Runnable runnable, RunnableScheduledFuture<V> task) {


//            task.cancel(false);
        return task;
    }




}
