package com.example.own.common.executor;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.example.own.common.constant.OwnConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.skywalking.apm.toolkit.trace.CallableWrapper;
import org.apache.skywalking.apm.toolkit.trace.RunnableWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ExecutorConfigurationSupport;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.*;

/**
 * @desc: 1、 java.util.concurrent.FutureTask cannot be cast to com.alibaba.ttl.TtlRunnable
 * @author: 英布 
 * @date: 2022/11/18 2:58 下午
 * 
 */
 
public class ThreadPoolTaskExecutorHelper extends ExecutorConfigurationSupport
        implements AsyncListenableTaskExecutor {


    private static final ConcurrentHashMap<Object, String> TRACE_MAP = new ConcurrentHashMap(128);

    private static final Logger log = LoggerFactory.getLogger(ThreadPoolTaskExecutorHelper.class);

    private volatile int corePoolSize = 1;

    private int maxPoolSize = Integer.MAX_VALUE;

    private int keepAliveSeconds = 60;

    private int queueCapacity = Integer.MAX_VALUE;

    private boolean allowCoreThreadTimeOut = false;

    private ThreadPoolExecutor threadPoolExecutor;

    private final Map<String, Long> timeCount = new ConcurrentHashMap<>(128);

    private String threadPoolName;

    //线程池是否支持自适应调整
    private volatile boolean adaptiveEnable = false;

//    private volatile SlidingWindow increaseSlidingWindow;
//
//    private volatile SlidingWindow reduceSlidingWindow;

    private volatile Integer threadThreshold = 3;

    private volatile Integer timeMillisPerSlice = 60 * 1000;

    private volatile Integer windowSize = 4;

    private static final Integer THREAD_POOL_CORE_MAX_THRESHOLD = 100;

    private static final Integer THREAD_POOL_CORE_MIN_THRESHOLD = 10;

    public void setAdaptiveEnable(boolean adaptiveEnable) {
        this.adaptiveEnable = adaptiveEnable;
    }

    public void setThreadPoolName(String threadPoolName) {
        this.threadPoolName = threadPoolName;
    }

    public String getThreadPoolName() {
        return threadPoolName;
    }

    /**
     * Set the ThreadPoolExecutor's core pool size.
     * Default is 1.
     * <p><b>This setting can be modified at runtime, for example through JMX.</b>
     */
    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        if (this.threadPoolExecutor != null) {
            this.threadPoolExecutor.setCorePoolSize(corePoolSize);
        }
    }

    /**
     * Return the ThreadPoolExecutor's core pool size.
     */
    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    /**
     * Set the ThreadPoolExecutor's maximum pool size.
     * Default is {@code Integer.MAX_VALUE}.
     * <p><b>This setting can be modified at runtime, for example through JMX.</b>
     */
    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        if (this.threadPoolExecutor != null) {
            this.threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
        }
    }

    /**
     * Return the ThreadPoolExecutor's maximum pool size.
     */
    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    /**
     * Set the ThreadPoolExecutor's keep-alive seconds.
     * Default is 60.
     * <p><b>This setting can be modified at runtime, for example through JMX.</b>
     */
    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
        if (this.threadPoolExecutor != null) {
            this.threadPoolExecutor.setKeepAliveTime(keepAliveSeconds, TimeUnit.SECONDS);
        }
    }

    /**
     * Return the ThreadPoolExecutor's keep-alive seconds.
     */
    public int getKeepAliveSeconds() {
        return this.keepAliveSeconds;
    }

    /**
     * Set the capacity for the ThreadPoolExecutor's BlockingQueue.
     * Default is {@code Integer.MAX_VALUE}.
     * <p>Any positive value will lead to a LinkedBlockingQueue instance;
     * any other value will lead to a SynchronousQueue instance.
     *
     * @see LinkedBlockingQueue
     * @see SynchronousQueue
     */
    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getQueueCapacity() {
        if (this.threadPoolExecutor == null) {
            // Not initialized yet: assume no active threads.
            return 0;
        }
        return this.threadPoolExecutor.getQueue().remainingCapacity() + this.threadPoolExecutor.getQueue().size();
    }

    /**
     * Specify whether to allow core threads to time out. This enables dynamic
     * growing and shrinking even in combination with a non-zero queue (since
     * the max pool size will only grow once the queue is full).
     * <p>Default is "false".
     *
     * @see ThreadPoolExecutor#allowCoreThreadTimeOut(boolean)
     */
    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }


    /**
     * Note: This method exposes an {@link ExecutorService} to its base class
     * but stores the actual {@link ThreadPoolExecutor} handle internally.
     * Do not override this method for replacing the executor, rather just for
     * decorating its {@code ExecutorService} handle or storing custom state.
     */
    @Override
    protected ExecutorService initializeExecutor(
            ThreadFactory threadFactory, RejectedExecutionHandler rejectedExecutionHandler) {

        if (StringUtils.isEmpty(threadPoolName)) {
            throw new RuntimeException("threadPoolName is not initialized!");
        }

        BlockingQueue<Runnable> queue = createQueue(this.queueCapacity);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                this.corePoolSize, this.maxPoolSize, this.keepAliveSeconds, TimeUnit.SECONDS,
                queue, threadFactory, rejectedExecutionHandler) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                timeCount.put(String.valueOf(r.hashCode()), System.currentTimeMillis());
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                int activeCount = ThreadPoolTaskExecutorHelper.this.getActiveCount();
                int coreSize = ThreadPoolTaskExecutorHelper.this.getCorePoolSize();
                int largestPoolSize = ThreadPoolTaskExecutorHelper.this.getLargestPoolSize();
                long completedTaskCount = ThreadPoolTaskExecutorHelper.this.getCompletedTaskCount();
                long taskCount = ThreadPoolTaskExecutorHelper.this.getTaskCount();
                int queueSize = ThreadPoolTaskExecutorHelper.this.getQueueSize();

                Long startTime = timeCount.remove(String.valueOf(r.hashCode()));
                long duration = System.currentTimeMillis() - startTime;
//                log.info("tid:{},duration:{},corePoolSize:{},activeCount:{},completed:{},taskCount:{},queue:{},largestPoolSize:{}",
//                        getTraceIdFromTask(r),
//                        duration, coreSize, activeCount, completedTaskCount, taskCount, queueSize, largestPoolSize);
//                EventTrace.newGauge(ThreadPoolReporterConstants.THREAD_POOL_STATS)
//                        .addTag(ThreadPoolReporterConstants.HOST_TAG, HostHelper.getHost())
//                        .addTag(ThreadPoolReporterConstants.NAME_TAG, threadPoolName)
//                        .addTag(ThreadPoolReporterConstants.TYPE_TAG, ThreadPoolReporterConstants.ACTIVE_SIZE)
//                        .value(activeCount);
//                EventTrace.newGauge(ThreadPoolReporterConstants.THREAD_POOL_STATS)
//                        .addTag(ThreadPoolReporterConstants.HOST_TAG, HostHelper.getHost())
//                        .addTag(ThreadPoolReporterConstants.NAME_TAG, threadPoolName)
//                        .addTag(ThreadPoolReporterConstants.TYPE_TAG, ThreadPoolReporterConstants.CORE_SIZE)
//                        .value(coreSize);
//                EventTrace.newGauge(ThreadPoolReporterConstants.THREAD_POOL_STATS)
//                        .addTag(ThreadPoolReporterConstants.HOST_TAG, HostHelper.getHost())
//                        .addTag(ThreadPoolReporterConstants.NAME_TAG, threadPoolName)
//                        .addTag(ThreadPoolReporterConstants.TYPE_TAG, ThreadPoolReporterConstants.MAX_SIZE)
//                        .value(largestPoolSize);
//                EventTrace.newGauge(ThreadPoolReporterConstants.THREAD_POOL_TASK_COUNT)
//                        .addTag(ThreadPoolReporterConstants.HOST_TAG, HostHelper.getHost())
//                        .addTag(ThreadPoolReporterConstants.NAME_TAG, threadPoolName)
//                        .addTag(ThreadPoolReporterConstants.TYPE_TAG, ThreadPoolReporterConstants.COMPLETED_COUNT)
//                        .value(completedTaskCount);
//                EventTrace.newGauge(ThreadPoolReporterConstants.THREAD_POOL_DURATION)
//                        .addTag(ThreadPoolReporterConstants.HOST_TAG, HostHelper.getHost())
//                        .addTag(ThreadPoolReporterConstants.NAME_TAG, threadPoolName)
//                        .addTag(ThreadPoolReporterConstants.TYPE_TAG, ThreadPoolReporterConstants.DURATION).value(duration);
//                EventTrace.newMetric(ThreadPoolReporterConstants.THREAD_POOL_QUEUE)
//                        .addTag(ThreadPoolReporterConstants.HOST_TAG, HostHelper.getHost())
//                        .addTag(ThreadPoolReporterConstants.NAME_TAG, threadPoolName)
//                        .addField(ThreadPoolReporterConstants.QUEUE_CAPACITY_SIZE, Field.newGaugeField(queueCapacity))
//                        .addField(ThreadPoolReporterConstants.QUEUE_CURRENT_SIZE, Field.newGaugeField(queueSize)).finish();
                parameterAdaptive(r);
                exceptionHandle(r, t);
//                removeTraceId(r);
            }
        };

        if (this.allowCoreThreadTimeOut) {
            executor.allowCoreThreadTimeOut(true);
        }

        this.threadPoolExecutor = executor;
        return executor;
    }

//
//    private String getTraceIdFromTask(Runnable r) {
//
//        return TRACE_MAP.get(((TtlRunnable) r).unwrap());
//    }


//    private void removeTraceId(Runnable r) {
//
//        TRACE_MAP.remove(((TtlRunnable) r).unwrap());
//    }

    private void parameterAdaptive(Runnable r) {
        if (!adaptiveEnable) {
            return;
        }
        //判断是否需要缩容
        reduceCoreSize(r);

        //判断是否需要扩容
        increaseCoreSize(r);
    }

    private void reduceCoreSize(Runnable r) {
        if (getActiveCount() > corePoolSize / 2) {
            return;
        }
        if (corePoolSize == THREAD_POOL_CORE_MIN_THRESHOLD) {
            return;
        }
//        SlidingWindow reduceSlidingWindow = getReduceSlidingWindow();
//        if (!reduceSlidingWindow.slideOverThrehold()) {
//            return;
//        }
//        log.info("tid:{},线程池已经进行缩容缩容前corePoolSize:{}", getTraceIdFromTask(r), corePoolSize);
        setCorePoolSize(corePoolSize / 2 < THREAD_POOL_CORE_MIN_THRESHOLD ? THREAD_POOL_CORE_MIN_THRESHOLD : corePoolSize / 2);
//        log.info("tid:{},线程池已经进行缩容,缩容后corePoolSize:{}", getTraceIdFromTask(r), corePoolSize);
    }

    private void increaseCoreSize(Runnable r) {
        if (threadPoolExecutor.getQueue().size() < queueCapacity) {
            return;
        }

        //线程池队列的大小达到队列阈值，滑动窗口加一
//        SlidingWindow slidingWindow = getIncreaseSlidingWindow();
//        if (!slidingWindow.slideOverThrehold()) {
//            return;
//        }

        int corePoolSize = getCorePoolSize();
        if (corePoolSize >= THREAD_POOL_CORE_MAX_THRESHOLD) {
            //避免最大线程数小于核心线程数
            setMaxPoolSize(THREAD_POOL_CORE_MAX_THRESHOLD);
            log.error("核心线程数已经达到100，请检查服务流量是否正常");
            return;
        }
//        log.info("tid:{},线程池已经进行扩容,扩容前corePoolSize:{}", getTraceIdFromTask(r), corePoolSize);
        setCorePoolSize(corePoolSize * 2 > THREAD_POOL_CORE_MAX_THRESHOLD ? THREAD_POOL_CORE_MAX_THRESHOLD : corePoolSize * 2);
//        log.info("tid:{},线程池已经进行扩容,扩容后corePoolSize:{}", getTraceIdFromTask(r), corePoolSize);
    }


//    public SlidingWindow getIncreaseSlidingWindow() {
//        if (increaseSlidingWindow == null) {
//            synchronized (this) {
//                if (increaseSlidingWindow == null) {
//                    increaseSlidingWindow = new SlidingWindow(timeMillisPerSlice, windowSize, threadThreshold);
//                }
//            }
//        }
//        return increaseSlidingWindow;
//    }

//    public SlidingWindow getReduceSlidingWindow() {
//        if (reduceSlidingWindow == null) {
//            synchronized (this) {
//                if (reduceSlidingWindow == null) {
//                    reduceSlidingWindow = new SlidingWindow(timeMillisPerSlice, windowSize, threadThreshold);
//                }
//            }
//        }
//        return reduceSlidingWindow;
//    }

    private void exceptionHandle(Runnable r, Throwable t) {
        if (t != null) {
            exceptionTrace(t, threadPoolName);
        }
        if (r instanceof FutureTask) {
            try {
                ((FutureTask) r).get();
            } catch (InterruptedException e) {
                exceptionTrace(t, threadPoolName);
            } catch (ExecutionException e) {
                exceptionTrace(t, threadPoolName);
            }
        }
    }

    public static void exceptionTrace(Throwable t, String threadPoolName) {
        log.warn("线程池处理发生异常", t);
        String[] messages = t.getMessage().split("\'");
        String code = "";
        if (messages.length > 1) {
            code = messages[1];
        }
//        EventTrace.newCounter(ThreadPoolReporterConstants.THREAD_POOL_EXCEPTION)
//                .addTag(ThreadPoolReporterConstants.HOST_TAG, HostHelper.getHost())
//                .addTag(ThreadPoolReporterConstants.NAME_TAG, threadPoolName)
//                .addTag(ThreadPoolReporterConstants.ERROR_CODE, code)
//                .addTag(ThreadPoolReporterConstants.TYPE_TAG, ThreadPoolReporterConstants.TASK_EXCEPTION)
//                .once();
    }


    /**
     * Create the BlockingQueue to use for the ThreadPoolExecutor.
     * <p>A LinkedBlockingQueue instance will be created for a positive
     * capacity value; a SynchronousQueue else.
     *
     * @param queueCapacity the specified queue capacity
     * @return the BlockingQueue instance
     * @see LinkedBlockingQueue
     * @see SynchronousQueue
     */
    protected BlockingQueue<Runnable> createQueue(int queueCapacity) {
        if (queueCapacity > 0) {
            return new LinkedBlockingQueue<>(queueCapacity);
        } else {
            return new SynchronousQueue<>();
        }
    }

    /**
     * Return the underlying ThreadPoolExecutor for native access.
     *
     * @return the underlying ThreadPoolExecutor (never {@code null})
     * @throws IllegalStateException if the ThreadPoolTaskExecutor hasn't been initialized yet
     */
    public ThreadPoolExecutor getThreadPoolExecutor() throws IllegalStateException {
        Assert.state(this.threadPoolExecutor != null, "ThreadPoolTaskExecutor not initialized");
        return this.threadPoolExecutor;
    }

    /**
     * Return the current pool size.
     *
     * @see ThreadPoolExecutor#getPoolSize()
     */
    public int getPoolSize() {
        if (this.threadPoolExecutor == null) {
            // Not initialized yet: assume core pool size.
            return this.corePoolSize;
        }
        return this.threadPoolExecutor.getPoolSize();
    }

    /**
     * Return the number of currently active threads.
     *
     * @see ThreadPoolExecutor#getActiveCount()
     */
    public int getActiveCount() {
        if (this.threadPoolExecutor == null) {
            // Not initialized yet: assume no active threads.
            return 0;
        }
        return this.threadPoolExecutor.getActiveCount();
    }

    public int getLargestPoolSize() {
        if (this.threadPoolExecutor == null) {
            // Not initialized yet: assume no active threads.
            return 0;
        }
        return this.threadPoolExecutor.getLargestPoolSize();
    }

    public long getCompletedTaskCount() {
        if (this.threadPoolExecutor == null) {
            // Not initialized yet: assume no active threads.
            return 0;
        }
        return this.threadPoolExecutor.getCompletedTaskCount();
    }

    public long getTaskCount() {
        if (this.threadPoolExecutor == null) {
            // Not initialized yet: assume no active threads.
            return 0;
        }
        return this.threadPoolExecutor.getTaskCount();
    }

    public int getQueueSize() {
        if (this.threadPoolExecutor == null) {
            // Not initialized yet: assume no active threads.
            return 0;
        }
        return this.threadPoolExecutor.getQueue().size();
    }

    private String getTraceId() {
        try {
            return MDC.get(OwnConstant.TRACE_ID);
        } catch (Exception e) {
            log.info("get trace id error", e);
            return "";
        }

    }

    @Override
    public void execute(Runnable task) {
        Executor executor = getThreadPoolExecutor();
        try {
            RunnableWrapper wrapper = RunnableWrapper.of(wrapRunnable(task));

//            TRACE_MAP.put(wrapper, getTraceId());

            TtlExecutors.getTtlExecutor(executor).execute(wrapper);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public void execute(Runnable task, long startTimeout) {
        execute(task);
    }

    @Override
    public Future<?> submit(Runnable task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            RunnableWrapper wrapper = RunnableWrapper.of(wrapRunnable(task));

//            TRACE_MAP.put(wrapper, getTraceId());

            return TtlExecutors.getTtlExecutorService(executor).submit(wrapper);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            CallableWrapper wrapper = CallableWrapper.of(wrapCallable(task));

//            TRACE_MAP.put(wrapper, getTraceId());

            return TtlExecutors.getTtlExecutorService(executor).submit(wrapper);
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public ListenableFuture<?> submitListenable(Runnable task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            ListenableFutureTask<Object> future = new ListenableFutureTask<>(wrapRunnable(task), null);
            executor.execute(future);
            return future;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    @Override
    public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
        ExecutorService executor = getThreadPoolExecutor();
        try {
            ListenableFutureTask<T> future = new ListenableFutureTask<>(wrapCallable(task));
            executor.execute(future);
            return future;
        } catch (RejectedExecutionException ex) {
            throw new TaskRejectedException("Executor [" + executor + "] did not accept task: " + task, ex);
        }
    }

    private void writeObject(ObjectOutputStream stream)
            throws IOException {
        stream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
    }

    /**
     * 装饰runnable，添加子线程traceId值、父级线程的TyphoonSdkContext
     * @param runnable
     * @return
     */
    private Runnable wrapRunnable(Runnable runnable) {
        String traceId = getTraceId();
//        TyphoonSdkContext generalSdkContext = getTyphoonSdkContext();
        Runnable newRunnable = () -> {
            try {
//                //1. 传递traceid
                MDC.put(OwnConstant.TRACE_ID, traceId);
//                //3. 设置父级typhoonContext
//                setAsyncSdkContext(generalSdkContext);
                runnable.run();
            } finally {
//                TyphoonSessionContext.remove();
                MDC.remove(OwnConstant.TRACE_ID);
            }
        };
        return newRunnable;
    }

    /**
     * 装饰callable，添加子线程traceId值、父级线程的TyphoonSdkContext
     * @param task
     * @param <T>
     * @return
     */
    private <T> Callable<T> wrapCallable(Callable<T> task) {
        String traceId = getTraceId();
//        TyphoonSdkContext generalSdkContext = getTyphoonSdkContext();
        Callable<T> newCallable = () -> {
            try {
                //1. 传递traceid
//                MDC.put(AppFilterHelper.KEY_TRACE_ID, traceId);
//                //3. 设置父级typhoonContext
//                setAsyncSdkContext(generalSdkContext);
                return task.call();
            } finally {
//                TyphoonSessionContext.remove();
//                MDC.remove(AppFilterHelper.KEY_TRACE_ID);
            }
        };
        return newCallable;
    }

    /**
     * 先从TyphoonSessionContext获取当前线程的context，获取不到再从TyphoonContextConfig里获取
     *
     * @return
     */
//    private TyphoonSdkContext getTyphoonSdkContext() {
//        // 1.先尝试从TyphoonSessionContext获取当前线程的context
//        TyphoonSdkContext typhoonSdkContext = TyphoonSessionContext.getContext();
//        if (null != typhoonSdkContext) {
//            log.info("ThreadPoolTaskExecutorHelper getTyphoonSdkContext , get from TyphoonSessionContext, typhoonSdkContext: {}",
//                    typhoonSdkContext);
//            return typhoonSdkContext;
//        }
//
//        // 2.从TyphoonContextConfig里获取context
//        ApplicationContext applicationContext = SpringBeanUtil.getApplicationContext();
//        // 避免获取bean的时机在容器初始化完成前
//        if (null == applicationContext) {
//            log.warn("ThreadPoolTaskExecutorHelper getTyphoonSdkContext , applicationContext is not initialized");
//            return null;
//        }
//        TyphoonContextConfig typhoonContextConfig = applicationContext.getBean(TyphoonContextConfig.class);
//        TyphoonSdkContext generalSdkContext = null;
//        try {
//            generalSdkContext = typhoonContextConfig.tranAppContextToTyphoonContext();
//        } catch (Exception e) {
//            log.warn("ThreadPoolTaskExecutorHelper getTyphoonSdkContext error,", e);
//        }
//        return generalSdkContext;
//    }

    /**
     * 先从从ApplicationContext中获取TyphoonContextConfig
     * @param generalSdkContext
     */
//    private void setAsyncSdkContext(TyphoonSdkContext generalSdkContext) {
//        if (null == generalSdkContext) {
//            log.warn("ThreadPoolTaskExecutorHelper setAsyncSdkContext , generalSdkContext is null");
//            return;
//        }
//        TyphoonContextConfig.refreshContext(generalSdkContext);
//    }

}
