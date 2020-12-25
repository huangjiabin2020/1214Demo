package com.binge.webentity;

public enum AxiosStatus {


   OK(20000,"操作成功"),
   ERROR(50000,"操作失败"),
   EXT_ERROE(30000,"上传格式不支持"),
   FILE_TOOLONG(30001,"文件太大"),
   NOT_IMAGE(30002,"上传的不是一个图片"),
    VALID_FAILURE(30004,"表单验证失败"),
    USERNAME_NOT_EMPTY(40001,"用户名不能为空"),
   USERNAME_NOT_SURE(40002,"用户名不正确"),
    PASSWORD_NOT_EMPTY(40003,"密码不能为空"),
    PASSWORD_NOT_SURE(40004,"密码不正确"),
    TOKEN_VALID_FAILURE(44444,"未认证"),
    WEB_ERROR(50000,"前端错误"),
    JWT_ERROR(40005,"JWT出错"),
    INTERFACE_LIMIT(40006,"接口被限流")
    ;


    private int status;


    private String message;

    AxiosStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
