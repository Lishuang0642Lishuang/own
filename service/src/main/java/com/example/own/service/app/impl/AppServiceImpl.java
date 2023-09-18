package com.example.own.service.app.impl;

import com.example.own.core.batch.SqlBatchOperateComponent;
import com.example.own.core.mysql.IAppDao;
import com.example.own.core.mysql.bean.AppDO;
import com.example.own.core.mysql.mapper.AppMapper;
import com.example.own.service.app.IAppService;
import com.example.own.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

@Slf4j
@Service("appService")
public class AppServiceImpl extends BaseServiceImpl<AppMapper, AppDO> implements IAppService {

    @Resource
    IAppDao appDao;

    @Resource
    AppMapper appMapper;

    @Resource
    SqlBatchOperateComponent<AppDO> component;

    private LinkedBlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>(20);



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
        
        for (int i=0; i<300; i++) {

            AppDO newAppDO = new AppDO();
            newAppDO.setAppId(String.valueOf(RandomUtils.nextInt()));
            newAppDO.setName(String.valueOf(RandomUtils.nextInt()));
            newAppDO.setOrgId(String.valueOf(RandomUtils.nextInt()));
            newAppDO.setCreateTime(RandomUtils.nextLong());
            newAppDO.setCreateUser("sdf");
            newAppDO.setId(RandomUtils.nextLong());
            component.saveWithCyclicBarrier(this, newAppDO);

        }
    }


    public static void main(String[] args) {
        AppDO appDO = new AppDO();
        System.out.println(appDO.getClass().getName());
    }


}
