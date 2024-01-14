package com.example.own.api.controller;

import com.example.own.api.dto.Old;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/option")
public class OptionController {

    @ResponseBody
    @PostMapping("/testOption")
    public void testOption() throws Exception {

        log.info("before exception");
        Optional.ofNullable(getOld()).orElseThrow(()->new Exception("orElseThrowException"));
        log.info("sdf");



    }

    private Old getOld() {
        return null;

    }
 }
