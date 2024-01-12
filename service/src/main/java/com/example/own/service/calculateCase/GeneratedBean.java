package com.example.own.service.calculateCase;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.List;

@Data
public class GeneratedBean {

    @ExcelIgnore
    Boolean isLogin;

    @ExcelIgnore
    String baseAddr;

    @ExcelProperty("url")
    String apiAddr;

    @ExcelProperty("请求方式")
    String reqMethod;

    @ExcelProperty("接口描述")
    String apiDesc;

    @ExcelIgnore
    String testMethodName;

    @ExcelIgnore
    String reqType;

    @ExcelIgnore
    List<String> params;

    @ExcelIgnore
    String reqData;

    @ExcelIgnore
    String queryData;
}
