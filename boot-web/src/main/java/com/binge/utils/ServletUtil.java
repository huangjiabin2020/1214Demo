package com.binge.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月18日
 * @description:
 **/
@Component
public class ServletUtil {



    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static HttpServletResponse getResponse() {

        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 指定返回值类型为JSON格式字符串
     *
     * @param jsonStr
     */
    public static void returnJsonStr(String jsonStr) {
        HttpServletResponse response = getResponse();
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  static void returnAxiosResult(Integer code, String msg, Object data) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("code",code);
        map.put("msg",msg);
        map.put("data",data);
        JsonUtil jsonUtil = GetBean.getBean(JsonUtil.class);
        returnJsonStr(jsonUtil.obj2Str(map));
    }
    public  static void returnSuccessAxiosResult( String msg, Object data) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("code",200);
        map.put("msg",msg);
        map.put("data",data);
        JsonUtil jsonUtil = GetBean.getBean(JsonUtil.class);
        returnJsonStr(jsonUtil.obj2Str(map));
    }
}
