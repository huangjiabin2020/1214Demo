package com.binge.exception;

import com.binge.webentity.AxiosResult;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 佛祖保佑  永无BUG
 *
 * @author: HuangJiaBin
 * @date: 2020年 12月21日
 * @description:
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class MyException extends RuntimeException implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public MyException(AxiosResult axiosResult){
        this.code= (Integer) axiosResult.get("status");
        this.msg= (String) axiosResult.get("message");
        this.data=axiosResult.get("data");
    }


    public MyException(String msg){
        this.code= 9999;
        this.msg= msg;
    }
}
