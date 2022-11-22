package com.example.own.core.mysql.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.own.core.mysql.base.BaseDO;

public interface IBaseDao<T extends BaseDO> extends IService<T> {
}
