package com.example.own.common.aspect;

import com.example.own.common.annotation.FlowLimit;
import com.example.own.common.config.FlowLimitProperties;
import com.example.own.common.handler.FlowLimitHandler;
import com.example.own.common.handler.HandlerFactory;
import com.example.own.common.model.LimitDetail;
import com.example.own.common.utils.CopyObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @desc: 限流组件的切面
 * @author: link.li
 * @date: 2023/10/29
 *
 */
@Slf4j
@Aspect
@Component
public class FlowLimitAspect {

    @Resource
    private HandlerFactory handlerFactory;

    @Resource
    private FlowLimitProperties flowLimitProperties;

    @Pointcut("@annotation(com.example.own.common.annotation.FlowLimit)")
    public void requestPointcut(){}

    @Around("requestPointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Method method = ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod();
        FlowLimit flowLimit = method.getAnnotation(FlowLimit.class);
        // 1、放行策略中可能涉及到黑白名单，无法在注解中进行配置，需要走nacos  2、跟nacos中的配置合并，同时存在的时候，以nacos为准，便于线上进行切换
        LimitDetail limitDetail = getLimitDetail(flowLimit);
        // todo ls 2、根据identifier解析出请求中的值，进行设置，作为key
        limitDetail.setIdentifier("");



        FlowLimitHandler flowLimitHandler = handlerFactory.getHandlerByLimitType(flowLimit.limitType());
        Boolean result = flowLimitHandler.addRequest(limitDetail);

        //todo  ls 执行拒绝策略





        return proceedingJoinPoint.proceed();
    }

    /**
     * 根据注解中的配置和nacos中的配置，合并最终配置
     * @param flowLimit
     * @return
     */
    private LimitDetail getLimitDetail(FlowLimit flowLimit) {

        LimitDetail limitDetail = CopyObjectUtils.copyAtoB(flowLimit, LimitDetail.class);

        // 1、nacos中没有，则直接返回
        String group = limitDetail.getGroup();
        LimitDetail nacosLimitDetail = flowLimitProperties.getGroup().get(group);
        if (ObjectUtils.isEmpty(nacosLimitDetail)) {
            return limitDetail;
        }

        //2、如果nacos中有配置，则替换掉flowLimit中的配置
        if (ObjectUtils.isNotEmpty(nacosLimitDetail.getLimitType())) {
            limitDetail.setLimitType(nacosLimitDetail.getLimitType());
        }

        if (ObjectUtils.isNotEmpty(nacosLimitDetail.getMaxTimes())) {
            limitDetail.setMaxTimes(nacosLimitDetail.getMaxTimes());
        }

        if (ObjectUtils.isNotEmpty(nacosLimitDetail.getUnit())) {
            limitDetail.setUnit(nacosLimitDetail.getUnit());
        }

        if (ObjectUtils.isNotEmpty(nacosLimitDetail.getUnitTime())) {
            limitDetail.setUnitTime(nacosLimitDetail.getUnitTime());
        }

        //3、黑白名单设置一下
        limitDetail.setBlackList(nacosLimitDetail.getBlackList());
        limitDetail.setWhiteList(nacosLimitDetail.getWhiteList());

        return limitDetail;
    }

}