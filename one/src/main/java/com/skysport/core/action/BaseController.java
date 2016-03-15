package com.skysport.core.action;

import com.skysport.core.bean.BaseResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/13.
 */
public class BaseController<T> {

    private transient Logger logger = LoggerFactory.getLogger(BaseController.class);

    public static ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<HttpServletRequest>();

    protected <T> Map<String, Object> buildCallBackMap(BaseResp resp, T t) {
        Map<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("resp", resp);
        rtnMap.put("data", t);
        return rtnMap;
    }


}
