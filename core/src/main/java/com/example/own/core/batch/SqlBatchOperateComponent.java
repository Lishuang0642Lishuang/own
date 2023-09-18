package com.example.own.core.batch;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.own.common.lock.SpinLock;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @desc: mysql批量保存的组件，可以减少io,但要慎用，因为可能导致死锁
 * @author: link.li
 * @date: 2023/8/24
 *
 */
@Component
@Slf4j
public class SqlBatchOperateComponent<T> {

    @Resource
    BeanRoute beanRoute;

    private Map<String, List<T>> beanNameAndBeanListMap = new ConcurrentHashMap<>();


    public void saveWithCyclicBarrier(IService<T> service, T bean) {
        saveWithCyclicBarrier(service, bean.getClass().getName(), bean);
    }

    private void saveWithCyclicBarrier(IService<T> service, String beanName, T bean) {
        log.info("saveWithCyclicBarrier bean:{}",new Gson().toJson(bean));

        SpinLock spinLock = beanRoute.getSpinLock(beanName);
        AtomicInteger atomicInteger = beanRoute.getAtomicInteger(beanName);
        Long currentSpan = beanRoute.getCurrentSpan(beanName);


        spinLock.lock();
        try {
            Integer index = atomicInteger.incrementAndGet();
            long current = System.currentTimeMillis();

            if (!index.equals(beanRoute.getCycleSize()) && current <= currentSpan + 20000L) {

                getBeanList(beanName).add(bean);

            } else {
                List<T> saveBeanList = getBeanList(beanName);
                saveBeanList.add(bean);
                DefaultMapEntry entry = new DefaultMapEntry(service, saveBeanList);
                SqlBatchSaveRunnable.concurrentQueue.add(entry);

                //重新初始化
                beanRoute.setCurrentSpan(beanName, System.currentTimeMillis());
                atomicInteger.set(0);
                beanNameAndBeanListMap.put(beanName, new ArrayList<>(beanRoute.getCycleSize()));
            }

        } finally {
            spinLock.unlock();
        }










    }



    public List<T> getBeanList(String beanName) {
        List<T> beanList = beanNameAndBeanListMap.get(beanName);

        if (ObjectUtils.isEmpty(beanList)) {
            synchronized (this) {
                if (ObjectUtils.isEmpty(beanList)) {
                    beanList = new ArrayList<>(20);
                    beanNameAndBeanListMap.put(beanName, beanList);
                }
            }
        }
        return beanList;
    }









}