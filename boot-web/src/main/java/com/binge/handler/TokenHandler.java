//package com.binge.handler;
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.binge.common.Constant;
//import com.binge.exception.MyException;
//import com.binge.utils.JwtUtil;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//
///**
// * 佛祖保佑  永无BUG
// *
// * @author: HuangJiaBin
// * @date: 2020年 12月21日
// * @description:
// **/
//@Component
//public class TokenHandler {
//    @Autowired
//    RedisTemplate redisTemplate;
//
//    public boolean verifyToken(HttpServletRequest request) {
//        //authorization就是前端传给后端携带的jwt字段
//        System.err.println("走了拦截器");
//        String authorization = request.getHeader("Authorization");
//        if (StringUtils.isEmpty(authorization)){
//            throw new MyException("请求没有认证字段！");
//        }
//        DecodedJWT jwt = JwtUtil.verifyToken(authorization);
//        String uuid = jwt.getClaim("uuid").asString();
//        System.err.println("解析的uuid是"+uuid);
//        Object o = redisTemplate.opsForValue().get(Constant.REDIS_UUID + uuid);
//        return ObjectUtils.isNotEmpty(o);
//    }
//}
