package com.example.own.api.controller;

import com.example.own.common.utils.DateTimeUtils;
import com.example.own.core.mysql.bean.AppDO;
import com.example.own.service.app.IAppService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Slf4j
@Controller
public class AppController {

    @Resource
    IAppService appService;

    @Resource
    Executor ownExecutor;

    @ResponseBody
    @RequestMapping("/appList")
    public List<AppDO> getAppList() {
        log.info("getAppList:{}", "query");

        CompletableFuture.runAsync(()-> appService.getAppList(), ownExecutor);


        List<AppDO> appList = appService.getAppList();
        log.info("================================================================================================");
        Integer zero = 0;
        Integer num = 1/zero;

        return appList;
    }



    @ResponseBody
    @RequestMapping("/static")
    public String getStaticResult() {
        DateTimeUtils.parseMinuteToTime(10);
        return "hello";
    }
}
