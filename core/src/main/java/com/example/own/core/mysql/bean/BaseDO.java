package com.example.own.core.mysql.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class BaseDO implements Serializable {

    private static final long serialVersionUID = 1043977077277962412L;


    /**
     * 数据库记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 创建时间
     */
    private Long gmtCreate;

    /**
     * 修改时间
     */
    private Long gmtModified;

    /**
     * 环境隔离
     */
    private String env;

    /**
     * 状态标志(0-已删除,1-生效)
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 修改者
     */
    private String modifier;








}
