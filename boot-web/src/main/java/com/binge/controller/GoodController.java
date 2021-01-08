package com.binge.controller;


import com.binge.webentity.AxiosResult;
import io.swagger.annotations.Api;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
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

   @Autowired
   Test test;

    @GetMapping("test")
    public AxiosResult test() {
//        RLock mylock = redissonClient.getLock("mylock");
//
//        mylock.lock(5, TimeUnit.SECONDS);
//
//        mylock.unlock();


        System.err.println(test.getExcludeURI());
        System.err.println(test.getStudents());

        return AxiosResult.success();

    }



}
