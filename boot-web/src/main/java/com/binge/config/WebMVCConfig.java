package com.binge.config;

import com.binge.interceptor.JwtInterceptor;
import com.binge.interceptor.RequestLimitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月21日
 * @description:
 **/
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    JwtInterceptor jwtInterceptor;
    @Autowired
    RequestLimitInterceptor requestLimitInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        System.err.println("jwt拦截器注册了");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/doc.html", "/swagger-resources/**");
        registry.addInterceptor(requestLimitInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/doc.html", "/swagger-resources/**");
    }
}
