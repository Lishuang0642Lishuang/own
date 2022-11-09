package com.example.own.service.dto;

import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

public class ChangeHistoryDTO implements Serializable {

    private static final long serialVersionUID = -2978582617072417423L;

    private String id;

    /**
     * 来源
     */
    private String source;

    /**
     * appId或者pid 因为名称可能会改变
     */
    private String sourceId;
    /**
     * 词条code
     */
    private String code;

    /**
     * 当前词条的最后更新时间
     */
    private String lastUpdated;

    /**
     * 历史操作类型，新增/删除/更新
     *
     */
    private String operateType;

    /**
     * 编辑者
     */
    private String editor;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 内部私有，不反馈<br/>
     * 默认：0，release：1,其他：-1
     */
    private Integer env;

    /**
     * 插入时间
     */
    private Long gmtModify;

    /**
     * 对应预发和线上
     */
    private String tuyaEnv;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getEnv() {
        return env;
    }

    public void setEnv(Integer env) {
        this.env = env;
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
}
