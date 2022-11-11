package com.example.own.api.controller;

import com.example.own.service.IChangeHistoryService;
import com.example.own.service.IMongoAggregateService;
import com.example.own.service.dto.ChangeHistoryDTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class ChangeHistoryController {



    @Resource
    IChangeHistoryService changeHistoryService;


    @ResponseBody
    @RequestMapping("/addChangeHistoryList")
    public void addChangeHistoryList(){


        List<ChangeHistoryDTO> changeHistoryDTOList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ChangeHistoryDTO changeHistoryDTO = new ChangeHistoryDTO();
            changeHistoryDTO.setCode("code" + i/5);
            changeHistoryDTO.setSource("pid" + i);
            changeHistoryDTO.setEditor("editor");
            changeHistoryDTO.setOperateType("operateType" + i/10);
            changeHistoryDTO.setTuyaEnv("pre");
            changeHistoryDTO.setStatus(i);
            changeHistoryDTOList.add(changeHistoryDTO);
        }

        changeHistoryService.insertChangeHistoryList(changeHistoryDTOList);
    }



    //1、用postman调用的话，如果选post模式，在body里面放内容，需要添加  @ResponseBody
    @ResponseBody
    @RequestMapping("/addChangeHistory")
    public void addChangeHistory(@RequestBody String history) {

        ChangeHistoryDTO changeHistoryDTO = new Gson().fromJson(history, ChangeHistoryDTO.class);

        changeHistoryService.insertChangeHistory(changeHistoryDTO);
    }




}
