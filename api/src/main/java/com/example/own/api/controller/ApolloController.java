package com.example.own.api.controller;

import com.example.own.core.apollo.ApolloConfigUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @desc: 用来测试apollo的controller
 * @author: 英布 
 * @date: 2022/11/19 9:09 下午
 * 
 */
 
public class ApolloController {

    @ResponseBody
    @RequestMapping("/apollo")
    public String testHello() {


        String value = ApolloConfigUtils.getConfigByNamespaceAndKey("gh", "io");
        return value;
    }
}
