package com.binge.annotation;

import java.lang.annotation.*;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月22日
 * @description:
 **/
@Inherited
//类和方法上
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {
    // 在 second 秒内，最大只能请求 maxCount 次
    int second();
    int maxCount();
}
