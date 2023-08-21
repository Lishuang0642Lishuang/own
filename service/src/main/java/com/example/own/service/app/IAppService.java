package com.example.own.service.app;

import com.example.own.core.mysql.bean.AppDO;

import java.util.List;

public interface IAppService {

    List<AppDO> getAppList();

    List<AppDO> unionQuery(String name, String orgId);
}
