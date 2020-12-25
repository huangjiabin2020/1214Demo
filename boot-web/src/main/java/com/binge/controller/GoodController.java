package com.binge.controller;


import com.binge.webentity.AxiosResult;
import io.swagger.annotations.Api;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author binge
 * @since 2020-12-22
 */
@Api("商品模块")
@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    RedissonClient redissonClient;

    public AxiosResult test(){
        RLock mylock = redissonClient.getLock("mylock");

        mylock.lock(5, TimeUnit.SECONDS);

        mylock.unlock();

        return AxiosResult.success();
    }

}
