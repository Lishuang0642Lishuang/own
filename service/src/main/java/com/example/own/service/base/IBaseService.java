package com.example.own.service.base;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.own.core.mysql.base.BaseDO;

import java.util.Collection;

public interface IBaseService<T extends BaseDO> extends IService<T> {

    boolean logicDelete(T entity);

    boolean logicDelete(Collection<T> entityList);

    boolean logicDelete(LambdaUpdateWrapper<T> updateWrapper);

}
