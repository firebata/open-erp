package com.skysport.core.filter;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

/**
 * 说明:
 * Created by zhangjh on 2015/12/1.
 */
public class LoginInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    private static final Set<String> IGNORE_URIS = new HashSet<>();

    static {
        IGNORE_URIS.add("/login.jsp");
        IGNORE_URIS.add("/login");
        IGNORE_URIS.add("/loginout");
        IGNORE_URIS.add("/index.jsp");
        IGNORE_URIS.add("/system/permission/qrMenu");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if ("GET".equalsIgnoreCase(request.getMethod())) {
//            RequestUtil.saveRequest();
//        }
        log.info("==============执行顺序: 1、preHandle================");
        String requestUri = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        if (IGNORE_URIS.contains(url)) {
            return true;
        } else {
            HttpSession session = request.getSession();
            UserInfo user = UserUtils.getUserFromSession(session);
            if (user == null) {
                log.info("Interceptor：跳转到login页面！");
                request.getRequestDispatcher("/page/index.jsp").forward(request, response);
                return false;
            } else {

                //鉴定用户是否具有权限
                return true;
            }

        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {


    }


}
