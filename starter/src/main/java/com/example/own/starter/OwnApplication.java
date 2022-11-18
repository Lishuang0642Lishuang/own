package com.example.own.starter;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * @desc: 1、spring task必须要@Async才能异步执行
 * @author: 英布
 * @date: 2022/11/18 2:53 下午
 *
 */

//@EnableApolloConfig()
@EnableScheduling
@EnableAsync
@SpringBootApplication(scanBasePackages={"com.example.own"})
public class OwnApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnApplication.class, args);
    }

}
