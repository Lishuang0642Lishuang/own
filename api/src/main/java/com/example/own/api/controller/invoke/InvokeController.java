package com.example.own.api.controller.invoke;

import com.example.own.service.app.IAppService;
import com.example.own.service.invoke.IInvokeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
public class InvokeController {

    @Resource
    IInvokeService invokeService;


    @ResponseBody
    @RequestMapping("/invoke")
    public void invokeRpc() {

        invokeService.invokeRpc();
    }
}
