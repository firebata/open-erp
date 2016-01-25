package com.skysport.inerfaces.constant.develop;

/**
 * 说明:开发模块的返回码
 * Created by zhangjh on 2015/9/2.
 */
public enum DevelopmentReturnConstant {

    PROJECT_CANNOT_EDIT("100004", "不能修改主项目信息");

    private String code;

    private String msg;


    DevelopmentReturnConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
