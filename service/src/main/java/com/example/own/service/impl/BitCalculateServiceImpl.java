package com.example.own.service.impl;


import com.example.own.service.IBitCalculateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @desc: 位运算
 * @author: 英布
 * @date: 2022/11/11 3:53 下午
 *
 */
@Service("bitCalculateService")
@Slf4j
public class BitCalculateServiceImpl implements IBitCalculateService {


    @Override
    public void bitMove() {
        String bit = "0011000";
        String movedFrontBit = (bit + bit).substring(1,8);
        log.info("BitCalculateServiceImpl movedFrontBit:{}", movedFrontBit);

        String movedBehindBit = (bit + bit).substring(6,13);
        log.info("BitCalculateServiceImpl movedBehindBit:{}", movedBehindBit);

    }


    public static void main(String[] args) {
        String bit = "0011000";
        String movedFrontBit = (bit + bit).substring(1,8);
        System.out.println(movedFrontBit);

        String movedBehindBit = (bit + bit).substring(6,13);
        System.out.println(movedBehindBit);
    }
}
