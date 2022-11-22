package com.example.own.api.controller;

import com.example.own.core.mysql.bean.AppDO;
import com.example.own.service.app.IAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Controller
public class AppController {

    @Resource
    IAppService appService;

    @ResponseBody
    @RequestMapping("/appList")
    public List<AppDO> getAppList() {

        return appService.getAppList();
    }
}
