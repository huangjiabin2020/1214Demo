package com.binge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月23日
 * @description:
 **/
@Configuration
public class ScheduledConfig {

    @Bean("myTaskScheduler")
    public ThreadPoolTaskScheduler taskExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setPoolSize(20);
        executor.setThreadNamePrefix("taskExecutor-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(300);
        return executor;
    }
}