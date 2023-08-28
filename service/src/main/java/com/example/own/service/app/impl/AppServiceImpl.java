package com.example.own.service.app.impl;

import com.example.own.core.batch.SqlBatchOperateComponent;
import com.example.own.core.mysql.IAppDao;
import com.example.own.core.mysql.bean.AppDO;
import com.example.own.core.mysql.mapper.AppMapper;
import com.example.own.service.app.IAppService;
import com.example.own.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("appService")
public class AppServiceImpl extends BaseServiceImpl<AppMapper, AppDO> implements IAppService {

    @Resource
    IAppDao appDao;

    @Resource
    AppMapper appMapper;

    @Resource
    SqlBatchOperateComponent<AppDO> component;

    @Resource
    LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(20);



    @Override
    public List<AppDO> getAppList() {
        log.info("getAppList:{}", "appList");
        return appDao.list();
    }

    @Override
    public List<AppDO> unionQuery(String name, String orgId) {

       List<AppDO> appDOList = appMapper.unionQueryDevice(name, orgId);
       log.info("appDOList:{}", appDOList);


        return appDOList;
    }

    @Override
    public void baseSave(AppDO appDO) {




        ThreadPoolExecutor sqlBatchExecutor =
                new ThreadPoolExecutor(1, 2, 60L, TimeUnit.SECONDS, blockingQueue){

                };
        sqlBatchExecutor.execute(() ->{







        });




//        component.saveWithCyclicBarrier(this, appDO);
    }


    public static void main(String[] args) {
        AppDO appDO = new AppDO();
        System.out.println(appDO.getClass().getName());
    }


}
