package com.example.own.service.user.impl;

import com.example.own.core.batch.SqlBatchOperateComponent;
import com.example.own.core.mysql.bean.UserDO;
import com.example.own.core.mysql.mapper.UserMapper;
import com.example.own.service.base.BaseServiceImpl;
import com.example.own.service.user.IUserService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserMapper, UserDO> implements IUserService {

    @Resource
    SqlBatchOperateComponent<UserDO> component;

    @Override
    public void baseSave() {
        for (int i = 0; i< 300; i++) {
            UserDO userDO = UserDO.builder().id(RandomUtils.nextLong()).sex(String.valueOf(RandomUtils.nextInt())).name(String.valueOf(RandomUtils.nextInt())).createUser("sdf").createTime(RandomUtils.nextLong()).build();
            component.saveWithCyclicBarrier(this, userDO);
        }
    }
}
