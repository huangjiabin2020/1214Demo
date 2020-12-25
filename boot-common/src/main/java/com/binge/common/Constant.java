package com.binge.common;

import org.springframework.stereotype.Component;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月21日
 * @description:
 **/
@Component
public class Constant {
    public static final String REDIS_EMAIL = "login:email:";
    public static final String REDIS_JWT = "login:jwt:";
    public static final String REDIS_UUID = "uuid:user:";
    public static final String INTERFACE_LIMIT = "interface:limit:";
}
