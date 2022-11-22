package com.example.own.service.app.impl;

import com.example.own.core.mysql.IAppDao;
import com.example.own.core.mysql.bean.AppDO;
import com.example.own.service.app.IAppService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("appService")
public class AppServiceImpl implements IAppService {

    @Resource
    IAppDao appDao;

    @Override
    public List<AppDO> getAppList() {
        return appDao.list();
    }
}
