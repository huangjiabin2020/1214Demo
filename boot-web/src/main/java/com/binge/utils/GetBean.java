package com.binge.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author JiaBin Huang
 * @date 2020/10/24
 **/
@Component
public class GetBean implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    /**
     * 容器一旦启动，就执行这个方法，就能拿到 applicationContext，也就能根据类型拿到容器中的Bean
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GetBean.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> tClass) {
        return applicationContext.getBean(tClass);
    }
}
