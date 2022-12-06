package com.example.own.api.aspect;

import com.example.own.common.constant.OwnConstant;
import com.example.own.core.snowflake.SnowFlakeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Aspect
@Component
public class TraceAspect {

    @Resource
    SnowFlakeGenerator snowFlakeGenerator;


    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestPointcut(){}


    @Around("requestPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MDC.put(OwnConstant.TRACE_ID, snowFlakeGenerator.getNextId());
        log.info("set traceId:{}", MDC.get(OwnConstant.TRACE_ID));
        return proceedingJoinPoint.proceed();
    }

}
