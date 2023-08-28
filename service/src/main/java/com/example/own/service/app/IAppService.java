package com.example.own.service.app;

import com.example.own.core.mysql.bean.AppDO;
import com.example.own.service.base.IBaseService;

import java.util.List;

public interface IAppService extends IBaseService<AppDO> {

    List<AppDO> getAppList();

    List<AppDO> unionQuery(String name, String orgId);

    void baseSave(AppDO appDO);
}
