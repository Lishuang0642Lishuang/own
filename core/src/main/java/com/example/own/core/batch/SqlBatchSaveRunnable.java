package com.example.own.core.batch;

import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

@Slf4j
@Component
public class SqlBatchSaveRunnable implements EnvironmentAware, InitializingBean, Runnable {

    public static final ConcurrentLinkedQueue<DefaultMapEntry> concurrentQueue = new ConcurrentLinkedQueue();

    private Environment environment;

    private DefaultMapEntry defaultMapEntry;




    @Override
    public void run() {

        while (true) {
            defaultMapEntry = concurrentQueue.poll();
            if (ObjectUtils.isEmpty(defaultMapEntry)) {
                LockSupport.parkNanos(1000000);
                continue;
            }
            log.info("key: {},value:{}", defaultMapEntry.getKey(), defaultMapEntry.getValue());

            try {
                IService<?> service = (IService<?>) defaultMapEntry.getKey();
                List beanList = (List) defaultMapEntry.getValue();
                log.info("beanList:{}", beanList);
                service.saveOrUpdateBatch(beanList);
            } catch (Exception e) {
                log.error("batch save sql error", e);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

//        String enable = environment.getProperty("mysql.batch.enable");
//
//        if (StringUtils.isNotEmpty(enable) && Boolean.parseBoolean(enable)) {
//            Thread thread = new Thread(this, "batch-save-sql");
//            thread.setDaemon(false);
//            thread.start();
//        }
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
