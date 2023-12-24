package com.example.own.common.executor;


import lombok.SneakyThrows;

import java.util.concurrent.FutureTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @desc: 线程池的执行器，用来测试
 * @author: lishuang
 * @date: 2023/11/14
 *
 */
public class ThreadPoolExecutor {


    @SneakyThrows
    public static void main(String[] args) throws InterruptedException {





        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(10);

        FutureTask future = new FutureTask(ThreadPoolExecutor::test);


        scheduledExecutorService.scheduleWithFixedDelay(future, 1,1, TimeUnit.SECONDS);


//        for (int i =0; i< 100; i++) {
//            if (i > 10) {
//                future.cancel(true);
//            }
//        }



        Thread.sleep(200000L);


    }

    @SneakyThrows
    public static Object test() {



        System.out.println("print test");
        return new Object();

    }
}
