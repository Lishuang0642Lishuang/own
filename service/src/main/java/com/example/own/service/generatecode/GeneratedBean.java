package com.example.own.service.generatecode;

import lombok.Data;

import java.util.List;

/**
 * @desc 解析好了的bean，方便转换成用例的参数
 * @author 英布
 * @date 2023/4/5 0:31
 */
@Data
public class GeneratedBean {

    Boolean isLogin;

    String apiDesc;

    String baseAddr;

    String apiAddr;

    String reqMethod;

    String testMethodName;

    String reqType;

    List<TypeParam> typeParams;

    String reqData;

    String queryData;

    /**
     * 是否包含 @RequestBody 注解
     */
    Boolean hashRequestBody;


    @Data
    static class TypeParam {

        /**
         * 参数类型
         */
        private String type;

        /**
         * 参数名
         */
        private String name;




    }

}