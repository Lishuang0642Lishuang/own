package com.example.own.service.executor;

import com.example.own.common.executor.ThreadPoolTaskExecutorHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @desc: 自己独立的线程池
 * @author: 英布
 * @date: 2022/11/18 10:03 上午
 *
 */
@Slf4j
@Configuration
public class ExecutorConfigs {



    //TODO 对不同业务线的线程池所有线程数量上限做限制
    /**
     * 默认等待时长
     */
    private static final Integer DEFAULT_AWAIT_TIME = 60;

    /**
     * 默认空闲销毁时长
     */
    private static final Integer DEFAULT_KEEP_ALIVE_SECOND = 60;

    /**
     * 默认核心线程数
     */
    private static final Integer DEFAULT_CORE_POOL_SIZE = 10;

    /**
     * 默认最大线程数
     */
    private static final Integer DEFAULT_MAX_POOL_SIZE = 20;

    /**
     * 默认缓冲执行任务的队列容量
     */
    private static final Integer DEFAULT_WORK_QUEUE_SIZE = 10;

    @Bean("ownExecutor")
    public Executor OwnExecutor() {

        ThreadPoolTaskExecutorHelper taskExecutor = new ThreadPoolTaskExecutorHelper();

        //线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        taskExecutor.setAwaitTerminationSeconds(DEFAULT_AWAIT_TIME);
        //核心线程数
        taskExecutor.setCorePoolSize(DEFAULT_CORE_POOL_SIZE);
        //最大线程数
        taskExecutor.setMaxPoolSize(DEFAULT_MAX_POOL_SIZE);
        //缓冲执行任务的队列
        taskExecutor.setQueueCapacity(DEFAULT_WORK_QUEUE_SIZE);
        //当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        taskExecutor.setKeepAliveSeconds(DEFAULT_KEEP_ALIVE_SECOND);

        //Reject策略预定义有四种：
        //(1)ThreadPoolExecutor.AbortPolicy策略，是默认的策略,处理程序遭到拒绝将抛出运行时 RejectedExecutionException。
        //(2)ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃.
        //(3)ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃.
        //(4)ThreadPoolExecutor.DiscardOldestPolicy策略，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序（如果再次失败，则重复此过程）
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setThreadNamePrefix("ownExecutor-async-thread-");
        taskExecutor.setThreadPoolName("ownExecutor");
        return taskExecutor;
    }
}
