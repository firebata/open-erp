package com.skysport.core.model.login.service.impl;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.login.helper.UserInfoServiceHelper;
import com.skysport.core.model.login.service.ILoginService;
import com.skysport.core.model.login.service.IUserService;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.constant.WebConstants;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/2.
 */
@Component("loginService")
public class LoginServiceImpl implements ILoginService {

    @Resource(name = "userService")
    private IUserService userService;


    /**
     * 1，用户名，如果session存在该用户，执行2，否则执行3
     * 2，检验用户是否锁定，如果没有锁定，返回6，锁定返回5；
     * 3，用用户名查询用户信息，如果有该用户，执行4，否则执行5
     * 4，校验密码，如果密码正确，执行2，如果密码错误，执行5
     * 5，返回登录页面
     * 6，返回成功
     *
     * @param user 用户登录输入信息
     * @return
     */
    @Override
    public ModelAndView login(UserInfo user) throws Exception {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserInfo userInDB = userService.queryInfoByUserInfoName(user.getUsername());
        //判断用户是否存在
        String msg = judgeLoginInfo(userInDB, user);
        if (null == msg) {
            HttpSession session = request.getSession();
            UserUtils.saveUserToSession(session, user);
            request.getSession().setAttribute(WebConstants.CURRENT_USER, userInDB);
            ModelAndView mav = new ModelAndView("main");
            Map<String, Object> result = new HashMap<>();
            result.put("username", userInDB.getAliases());
            mav.addAllObjects(result);
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("login");
            Map<String, Object> result = new HashMap<>();
            result.put("msg", msg);
            mav.addAllObjects(result);
            return mav;
        }

    }


    private String judgeLoginInfo(UserInfo userInDB, UserInfo user) throws Exception {
        String msg = null;
        if (null == userInDB || !UserInfoServiceHelper.SINGLETONE.isPwdRight(userInDB, user.getPassword())) {
            msg = "Account or password is incorrect";
        } else if (!UserInfoServiceHelper.SINGLETONE.isUserUnlocked(userInDB)) {
            msg = "Account is locked";
        }
        return msg;

    }
}
