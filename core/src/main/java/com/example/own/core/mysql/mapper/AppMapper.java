package com.example.own.core.mysql.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.own.core.mysql.bean.AppDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AppMapper extends BaseMapper<AppDO> {

    List<AppDO> unionQueryDevice(@Param("name") String name, @Param("orgId") String orgId );
}
