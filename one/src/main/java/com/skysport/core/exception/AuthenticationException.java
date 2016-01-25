package com.skysport.core.exception;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/21.
 */
public class AuthenticationException extends RuntimeException {

    private String code;
    private String message;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String code) {
        this.code = code;
    }

    public AuthenticationException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
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
