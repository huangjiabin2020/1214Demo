package com.binge;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: JiaBin Huang
 * @date: 2020年 12月14日
 * @description:
 **/
@SpringBootApplication
@MapperScan("com.binge.mapper")
@EnableAspectJAutoProxy
@EnableCaching
@EnableScheduling
@EnableTransactionManagement
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class);
    }
}
