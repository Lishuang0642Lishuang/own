package com.example.own.core.mysql.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.own.core.mysql.base.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@TableName("app")
public class AppDO extends BaseDO {

    private static final long serialVersionUID = 9143691038569978720L;

    @TableField(value = "app_id")
    private String appId;

    @TableField(value = "name")
    private String name;

    @TableField(value = "org_id")
    private String orgId;


}
