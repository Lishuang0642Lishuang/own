package com.example.own.api.controller.tool;

import com.example.own.core.mongo.entity.ChangeHistoryDO;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class GsonController {



    @ResponseBody
    @RequestMapping("/gson")
    public List<ChangeHistoryDO> getChangeHistoryDOList() {

        ChangeHistoryDO changeHistoryDO = new ChangeHistoryDO();
        changeHistoryDO.setCode("sdf");

        String gsonString = new Gson().toJson(changeHistoryDO);

        new Gson().fromJson(gsonString, ChangeHistoryDO.class);

        return null;
    }
}
