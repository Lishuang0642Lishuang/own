package com.example.own.core.mongo.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "multi_lang_change_history")
public class ChangeHistoryDO  extends BaseMongoDO {

    private static final long serialVersionUID = 8599928241933853431L;

    private String id;

    /**
     * 来源
     */
    @Field
    private String source;

    /**
     * appId或者pid 因为名称可能会改变
     */
    @Field
    private String sourceId;
    /**
     * 词条code
     */
    @Field
    private String code;

    /**
     * 当前词条的最后更新时间
     */
    @Field
    private String lastUpdated;

    /**
     * 历史操作类型，新增/删除/更新
     *
     */
    @Field
    private String operateType;

    /**
     * 编辑者
     */
    @Field
    private String editor;


    /**
     * 内部私有，不反馈<br/>
     * 默认：0，release：1,其他：-1
     */
    @Field
    private Integer env;

    /**
     * 插入时间
     */
    @Field
    private Long gmtModify;

    /**
     * 对应预发和线上
     */
    @Field
    private String tuyaEnv;
}
