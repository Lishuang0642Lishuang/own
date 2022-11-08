package com.example.own.starter;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableApolloConfig()
@SpringBootApplication(scanBasePackages={"com.example.own"})
public class OwnApplication {

    public static void main(String[] args) {
        SpringApplication.run(OwnApplication.class, args);
    }

}
