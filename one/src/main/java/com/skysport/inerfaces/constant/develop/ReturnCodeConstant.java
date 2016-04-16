package com.skysport.inerfaces.constant.develop;

/**
 * 说明:开发模块的返回码
 * Created by zhangjh on 2015/9/2.
 */
public enum ReturnCodeConstant {
    CLONE_FAIL("100003", "克隆对象失败"),
    PROJECT_CANNOT_EDIT("100004", "不能修改主项目信息"),

    UPDATE_BOM_MAINCOLOR_PARAM_ERR("100005", "修改bom主颜色时，传输的信息有误"),
    USER_IS_NOT_LOGINED("100006", "用户没有登录");
    private String code;

    private String msg;


    ReturnCodeConstant(String code, String msg) {
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
