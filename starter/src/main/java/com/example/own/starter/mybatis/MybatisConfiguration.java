package com.example.own.starter.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfiguration {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setProcessPropertyPlaceHolders(false);
        configurer.setBasePackage(getBasePackageCollect());
        return configurer;
    }


    private String getBasePackageCollect() {
        StringBuilder builder = new StringBuilder();
        builder.append("com.example.own");
        return builder.toString();
    }
}
