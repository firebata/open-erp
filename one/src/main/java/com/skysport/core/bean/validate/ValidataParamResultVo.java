package com.skysport.core.bean.validate;

/**
 * 类说明:参数校验结果信息
 * Created by zhangjh on 2015/7/13.
 */
public class ValidataParamResultVo {
    /**
     * 校验结果
     */
    private String result_code;
    /**
     * 为非空时，表示校验失败信息
     */
    private String result_msg;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_msg() {
        return result_msg;
    }

    public void setResult_msg(String result_msg) {
        this.result_msg = result_msg;
    }
}
