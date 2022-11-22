package com.example.own.core.mysql.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

public class BaseDaoImpl<M extends BaseMapper<T>, T extends BaseDO> extends ServiceImpl<M, T> implements IBaseDao<T> {
}
