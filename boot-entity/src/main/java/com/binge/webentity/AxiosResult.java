package com.binge.webentity;

import java.util.HashMap;


public class AxiosResult extends HashMap<String, Object> {

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String DATA = "data";

    public AxiosResult(AxiosStatus axiosStatus) {
        put(STATUS, axiosStatus.getStatus());
        put(MESSAGE, axiosStatus.getMessage());
    }

    /**
     * 设置成功的函数
     */

    public static AxiosResult success() {
        return new AxiosResult(AxiosStatus.OK);
    }


    public static AxiosResult success(AxiosStatus axiosStatus) {
        return new AxiosResult(axiosStatus);
    }


    public static AxiosResult success(Object obj) {
        AxiosResult axiosResult = new AxiosResult(AxiosStatus.OK);
        axiosResult.put(DATA, obj);
        return axiosResult;
    }


    public static AxiosResult error() {
        return new AxiosResult(AxiosStatus.ERROR);
    }

    public static AxiosResult error(AxiosStatus axiosStatus) {
        return new AxiosResult(axiosStatus);
    }



    public static AxiosResult error(AxiosStatus axiosStatus,Object obj) {
        AxiosResult axiosResult = new AxiosResult(axiosStatus);
        axiosResult.put(DATA,obj);

        return axiosResult;
    }

    public static AxiosResult error(Object obj) {
        AxiosResult axiosResult = new AxiosResult(AxiosStatus.ERROR);
        axiosResult.put(DATA, obj);
        return axiosResult;
    }
}
