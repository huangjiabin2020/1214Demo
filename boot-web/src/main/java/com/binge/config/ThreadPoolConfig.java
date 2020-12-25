package com.binge.config;

import io.netty.util.concurrent.ThreadPerTaskExecutor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月18日
 * @description:
 **/
@Component
@Data
//@PropertySource("/threadpool.properties") 挨个注入，比较麻烦
@ConfigurationProperties(prefix = "mythread")  //批量注入，需要指定前缀，后缀要和属性匹配
public class ThreadPoolConfig {
//    @Value("${threadpool.corePoolSize}")
    int corePoolSize;
//    @Value("${threadpool.maximumPoolSize}")
    int maximumPoolSize;
//    @Value("${threadpool.keepAliveTime}")
    long keepAliveTime;

    TimeUnit unit=TimeUnit.MINUTES;
    BlockingQueue<Runnable> workQueue=new LinkedBlockingDeque<>();
    ThreadFactory threadFactory=new CustomizableThreadFactory("binge-ThreadPool-");
    RejectedExecutionHandler handler;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,threadFactory);
    }


}
