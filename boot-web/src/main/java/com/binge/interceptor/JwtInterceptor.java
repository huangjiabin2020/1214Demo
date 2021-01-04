//package com.binge.interceptor;
//
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.binge.common.Constant;
//import com.binge.entity.User;
//import com.binge.exception.MyException;
//import com.binge.handler.TokenHandler;
//import com.binge.utils.GetBean;
//import com.binge.utils.JsonUtil;
//import com.binge.utils.JwtUtil;
//import com.binge.webentity.AxiosResult;
//import com.binge.webentity.AxiosStatus;
//import com.github.xiaoymin.knife4j.core.util.StrUtil;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 佛祖保佑  永无BUG
// *
// * @author: HuangJiaBin
// * @date: 2020年 12月21日
// * @description:
// **/
//@Component
//public class JwtInterceptor implements HandlerInterceptor {
//
//    public JwtInterceptor() {
//        System.err.println("JWT拦截器初始化了");
//    }
//
//    @Autowired
//    TokenHandler tokenHandler;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return true;
////        if (tokenHandler.verifyToken(request)){
////            return true;
////        }
////        throw new MyException(AxiosResult.error(AxiosStatus.JWT_ERROR));
//    }
//
//
//}
