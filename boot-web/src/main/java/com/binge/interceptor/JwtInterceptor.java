package com.binge.interceptor;

import com.binge.handler.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月21日
 * @description:
 **/
@Component
public class JwtInterceptor implements HandlerInterceptor {

    public JwtInterceptor() {
        System.err.println("JWT拦截器初始化了");
    }

    @Autowired
    TokenHandler tokenHandler;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
//        if (tokenHandler.verifyToken(request)){
//            return true;
//        }
//        throw new MyException(AxiosResult.error(AxiosStatus.JWT_ERROR));
    }


}
