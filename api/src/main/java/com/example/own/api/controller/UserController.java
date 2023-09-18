package com.example.own.api.controller;

import com.example.own.service.user.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource
    IUserService userService;

    @ResponseBody
    @PostMapping("/save")
    public void save() {
        userService.baseSave();
    }
}
