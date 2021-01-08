package com.binge.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月18日
 * @description:
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {
    @Pointcut("execution(* com.binge.controller.*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()")
    public void doBefore() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("\nrequest url:").append(request.getRequestURL()).append(":").append(request.getMethod()).append("\n");
        log.info(sb.toString());
    }

    /**
     * 指定了一个throwing属性，该属性值为ex,这允许在增强处理方法(doRecoveryActions()方法)中定义名为ex的形参，
     * 程序可通过该形参访问目标方法所抛出的异常。
     */
    @AfterThrowing(value = "controllerLog()",throwing = "ex")
    public void doAfterThrowing(Exception ex){
        log.error("Exception msg:{}",ex.getMessage());
    }
}
