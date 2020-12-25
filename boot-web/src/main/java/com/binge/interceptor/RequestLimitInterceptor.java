package com.binge.interceptor;

import com.binge.annotation.RequestLimit;
import com.binge.common.Constant;
import com.binge.exception.MyException;
import com.binge.utils.JsonUtil;
import com.binge.utils.ServletUtil;
import com.binge.webentity.AxiosResult;
import com.binge.webentity.AxiosStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月22日
 * @description:
 **/
@RestControllerAdvice
@Slf4j
public class RequestLimitInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * isAssignableFrom() 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，或是否是其超类或超接口
         * isAssignableFrom()方法是判断是否为某个类的父类
         * instanceof关键字是判断是否某个类的子类
         */
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            //HandlerMethod 封装方法定义相关的信息,如类,方法,参数等
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            // 获取方法中是否包含注解
            RequestLimit methodAnnotation = method.getAnnotation(RequestLimit.class);
            //获取类中是否包含注解，也就是controller 是否有注解
            RequestLimit classAnnotation = method.getDeclaringClass().getAnnotation(RequestLimit.class);
            // 如果 方法上有注解就优先选择方法上的参数，否则类上的参数
            RequestLimit requestLimit = methodAnnotation != null ? methodAnnotation : classAnnotation;
            StringBuilder sb = new StringBuilder();
            sb.append("\nrequest url:").append(request.getRequestURL()).append(":").append(request.getMethod()).append("\n");
            if (requestLimit != null) {
                if (isLimit(request, requestLimit,sb.toString())) {
                    log.info("\n接口限流："+sb.toString()+"\n");
                    throw new MyException(AxiosResult.error(AxiosStatus.INTERFACE_LIMIT));
                }
            }
        }
        return true;
    }

    /**
     * 判断请求是否受限
     *
     * @param request
     * @param requestLimit
     * @return
     */
    private boolean isLimit(HttpServletRequest request, RequestLimit requestLimit,String url) {

        //累计访问次数
        Integer count = (Integer) redisTemplate.opsForValue().get(Constant.INTERFACE_LIMIT + url);
        int max = requestLimit.maxCount();
        int timeLimit = requestLimit.second();
        if (count == null) {
            //第一次访问该接口
            redisTemplate.opsForValue().set(Constant.INTERFACE_LIMIT+url,1,timeLimit, TimeUnit.SECONDS);
        }else {
            redisTemplate.opsForValue().set(Constant.INTERFACE_LIMIT+url,count+1);
            return count > max;
        }
        return false;
    }
}
