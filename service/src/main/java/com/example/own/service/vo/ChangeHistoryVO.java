package com.example.own.service.vo;


public class ChangeHistoryVO {

    private String id;

    private String ids;

    private Integer status;

    private String tuyaEnv;

    private String editor;

    private String code;

    private Integer total;

    private String operateType;

    private String source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTuyaEnv() {
        return tuyaEnv;
    }

    public void setTuyaEnv(String tuyaEnv) {
        this.tuyaEnv = tuyaEnv;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "ChangeHistoryVO{" +
                "id='" + id + '\'' +
                ", ids='" + ids + '\'' +
                ", status=" + status +
                ", tuyaEnv='" + tuyaEnv + '\'' +
                ", editor='" + editor + '\'' +
                ", code='" + code + '\'' +
                ", total=" + total +
                '}';
    }
}
