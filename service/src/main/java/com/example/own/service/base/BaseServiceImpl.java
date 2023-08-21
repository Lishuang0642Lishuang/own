package com.example.own.service.base;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.own.core.mysql.base.BaseDO;
import java.util.Collection;

public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseDO> extends ServiceImpl<M, T> implements IBaseService<T> {


    @Override
    public boolean save(T entity) {

        entity.setCreateTime(System.currentTimeMillis());
        entity.setCreateUser("sdf");
        return false;
    }


    public boolean logicDelete(T entity) {
        entity.setStatus(0);
        entity.setUpdateTime(System.currentTimeMillis());

        return updateById(entity);
    }


    public boolean logicDelete(Collection<T> entityList) {

        entityList.forEach(
                item -> {
                    item.setStatus(0);
                    item.setUpdateTime(System.currentTimeMillis());
                }
        );
        return updateBatchById(entityList);
    }


    public boolean logicDelete(LambdaUpdateWrapper<T> updateWrapper) {
        updateWrapper.set(T::getStatus, 0);
        updateWrapper.set(T::getUpdateTime, System.currentTimeMillis());

        return update(updateWrapper);

    }

}
