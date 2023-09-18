package com.example.own.core.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.own.core.mysql.bean.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDO> {
}
