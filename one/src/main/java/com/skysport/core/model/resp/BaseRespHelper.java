package com.skysport.core.model.resp;

import com.skysport.core.bean.BaseRespVo;
import com.skysport.core.constant.CommonConstant;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public enum BaseRespHelper {
    SINGLETONE;
    BaseRespVo successResp = new BaseRespVo();

    {
        successResp.setReturn_code(CommonConstant.RETURN_SUCCESS);
        successResp.setResult_code(CommonConstant.RESULT_SUCCESS);
    }

    public BaseRespVo dealSucess() {
        return successResp;
    }


    public BaseRespVo dealFail(String return_code, String return_msg, String result_code, String err_code, String err_code_des) {
        BaseRespVo resp = new BaseRespVo();

        resp.setReturn_code(return_code);
        resp.setReturn_msg(return_msg);
        resp.setResult_code(result_code);
        resp.setErr_code(err_code);
        resp.setErr_code_des(err_code_des);

        return resp;


    }
}
