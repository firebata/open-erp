package com.skysport.core.exception;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 说明:
 * Created by zhangjh on 2016/2/18.
 */
public class ExceptionI18Message {
    public static String getLocaleMessage(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        RequestContext requestContext = new RequestContext(request);
        return requestContext.getMessage(key);
    }
}
