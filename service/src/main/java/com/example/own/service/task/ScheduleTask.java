package com.example.own.service.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @desc: 用springboot自带的task功能实现定时任务
 * @author: 英布 
 * @date: 2022/11/17 1:03 上午
 * 
 */

@Slf4j
@Component
public class ScheduleTask {


    private List<String> stringList = new ArrayList<>();



    // 1、加上该注解项目启动时就执行一次该方法
    // 2、TODO 了解postConstruct相关的生命周期
    // 3、和静态代码块什么区别

    @PostConstruct
    public void init(){
        stringList.add("ls");
        stringList.add("ls1");
        stringList.add("ls2");
        log.info("application start, stringList:{}", stringList);
        log.info("thread name:{}", Thread.currentThread().getName());
    }


    @Async("ownExecutor")
    @Scheduled(cron = "0 */1 * * * ?")
    public void process() throws Exception{
        stringList.forEach(log::info);
        log.info("process thread name:{}", Thread.currentThread().getName());
        Thread.sleep(120000L);
        log.info("process sleep");
    }

    @Async("ownExecutor")
    @Scheduled(cron = "0 */1 * * * ?")
    public void processOther() throws Exception {

        log.info("processOther thread name:{}", Thread.currentThread().getName());
        Thread.sleep(120000L);
        log.info("processOther sleep");
    }
}
