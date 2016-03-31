package com.skysport.core.utils;

import com.skysport.core.bean.permission.UserInfo;

import javax.servlet.http.HttpSession;

/**
 * 用户工具类
 *
 * @author HenryYan
 */
public class UserUtils {

    public static final String CURRENT_USER = "current_user";

    /**
     * 设置用户到session
     *
     * @param session
     * @param user
     */
    public static void saveUserToSession(HttpSession session, UserInfo user) {
        session.setAttribute(CURRENT_USER, user);
    }

    /**
     * 从Session获取当前用户信息
     *
     * @param session
     * @return
     */
    public static UserInfo getUserFromSession(HttpSession session) {
        Object attribute = session.getAttribute(CURRENT_USER);
        return attribute == null ? null : (UserInfo) attribute;
    }

    public static void removeUserFromSession(HttpSession session) {
        session.removeAttribute(CURRENT_USER);
    }

}
