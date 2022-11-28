package com.example.own.api.controller.middleware;

import com.example.own.core.apollo.ApolloConfigUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @desc: 用来测试apollo的controller
 * @author: 英布 
 * @date: 2022/11/19 9:09 下午
 * 
 */


@Slf4j
@Controller
public class ApolloController {

    @ResponseBody
    @RequestMapping("/apollo")
    public String testHello() {


        String value = ApolloConfigUtils.getConfigByNamespaceAndKey("gh", "io");
        return value;
    }
}
