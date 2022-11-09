package com.example.own.core.mongo.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "multi_lang_change_history")
public class ChangeHistoryDO  extends BaseMongoDO {

    private static final long serialVersionUID = 4185529965938438677L;
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

    private Integer status;

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public Long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Long gmtModify) {
        this.gmtModify = gmtModify;
    }

    public String getTuyaEnv() {
        return tuyaEnv;
    }

    public void setTuyaEnv(String tuyaEnv) {
        this.tuyaEnv = tuyaEnv;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChangeHistoryDO{" +
                "source='" + source + '\'' +
                ", sourceId='" + sourceId + '\'' +
                ", code='" + code + '\'' +
                ", lastUpdated='" + lastUpdated + '\'' +
                ", operateType='" + operateType + '\'' +
                ", editor='" + editor + '\'' +
                ", gmtModify=" + gmtModify +
                ", tuyaEnv='" + tuyaEnv + '\'' +
                '}';
    }
}
