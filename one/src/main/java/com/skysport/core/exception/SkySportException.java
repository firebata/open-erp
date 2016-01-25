package com.skysport.core.exception;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class SkySportException extends RuntimeException {
    private String code;
    private String message;

    public SkySportException() {
        super();
    }

    public SkySportException(String code) {
        this.code = code;
    }

    public SkySportException(String code, String message) {
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
