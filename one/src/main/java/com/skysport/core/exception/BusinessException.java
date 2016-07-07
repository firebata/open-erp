package com.skysport.core.exception;

import com.skysport.interfaces.constant.ReturnCodeConstant;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class BusinessException extends RuntimeException {
    private String code;
    private String message;

    public BusinessException() {
        super();
    }

    public BusinessException(String code) {
        this.code = code;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(ReturnCodeConstant returnCodeConstant) {
        super();
        new BusinessException(returnCodeConstant.getCode(), returnCodeConstant.getMsg());
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
