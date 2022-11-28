package com.example.own.starter.mybatis.configuration;

import com.example.own.starter.mybatis.interceptor.MybatisSqlPrintInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @desc: 用来打印sql
 * @author: 英布
 * @date: 2022/11/28 3:03 下午
 *
 */
@Slf4j
@Configuration
public class MybatisSqlPrintConfiguration {

    @Resource
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @PostConstruct
    public void addPrintInterceptor() {

        try{
            for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
                MybatisSqlPrintInterceptor
                        printInterceptor = new MybatisSqlPrintInterceptor();
                sqlSessionFactory.getConfiguration().addInterceptor(printInterceptor);
            }

        } catch (Exception e) {
            log.info("print sql error");
        }

    }
}
