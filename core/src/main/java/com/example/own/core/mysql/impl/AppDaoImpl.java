package com.example.own.core.mysql.impl;

import com.example.own.core.mysql.IAppDao;
import com.example.own.core.mysql.base.BaseDaoImpl;
import com.example.own.core.mysql.bean.AppDO;
import com.example.own.core.mysql.mapper.AppMapper;
import org.springframework.stereotype.Service;

@Service("appDao")
public class AppDaoImpl extends BaseDaoImpl<AppMapper, AppDO> implements IAppDao {
}
