package com.binge.webentity;

import lombok.Data;

/**
 * 单位名称：尚马教育
 * 员工描述：帅气的辉哥
 * 联系方式：568662344@qq.com
 * 创建日期：2020/10/30 17:39
 */
@Data
public class RestResult<T> {


    private int status;

    private String message;

    private T result;

    private RestResult() {
    }


    public static <T> RestResult<T> ok() {
        return getRestResult(AxiosStatus.OK, null);
    }


    public static <T> RestResult<T> ok(T result) {
        return getRestResult(AxiosStatus.OK, result);
    }


    public static <T> RestResult<T> ok(AxiosStatus axiosStatus, T result) {
        return getRestResult(axiosStatus, result);
    }


    public static <T> RestResult<T> error() {
        return getRestResult(AxiosStatus.ERROR, null);
    }


    public static <T> RestResult<T> error(T result) {
        return getRestResult(AxiosStatus.OK, result);
    }


    private static <T> RestResult<T> error(AxiosStatus axiosStatus, T result) {
        return getRestResult(axiosStatus, result);
    }


    private static <T> RestResult<T> getRestResult(AxiosStatus axiosStatus, T result) {
        RestResult<T> restResult = new RestResult<>();
        restResult.setStatus(axiosStatus.getStatus());
        restResult.setMessage(axiosStatus.getMessage());
        restResult.setResult(result);
        return restResult;
    }
}
