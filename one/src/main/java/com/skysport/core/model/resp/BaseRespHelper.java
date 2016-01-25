package com.skysport.core.model.resp;

import com.skysport.core.bean.BaseResp;
import com.skysport.core.constant.CommonConstant;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public enum BaseRespHelper {
    SINGLETONE;
    BaseResp successResp = new BaseResp();

    {
        successResp.setReturn_code(CommonConstant.RETURN_SUCCESS);
        successResp.setResult_code(CommonConstant.RESULT_SUCCESS);
    }

    public BaseResp dealSucess() {
        return successResp;
    }


    public BaseResp dealFail(String return_code, String return_msg, String result_code, String err_code, String err_code_des) {
        BaseResp resp = new BaseResp();

        resp.setReturn_code(return_code);
        resp.setReturn_msg(return_msg);
        resp.setResult_code(result_code);
        resp.setErr_code(err_code);
        resp.setErr_code_des(err_code_des);

        return resp;


    }
}
