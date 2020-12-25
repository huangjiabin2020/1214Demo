package com.binge.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月22日
 * @description:
 **/
//@Configuration
public class RedissonManagerConfig {
    public static void main(String[] args) {

    }

//    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useClusterServers()
                // use "rediss://" for SSL connection
                .addNodeAddress("redis://localhost:6379");
        // Sync and Async API
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }
}
