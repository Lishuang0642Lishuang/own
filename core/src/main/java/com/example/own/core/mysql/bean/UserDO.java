package com.example.own.core.mysql.bean;

import com.example.own.core.mysql.base.BaseDO;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class UserDO extends BaseDO {

    private static final long serialVersionUID = 457689295298092274L;

    private String name;

    private String sex;

    private String address;
}
