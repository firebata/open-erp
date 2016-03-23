package com.skysport.core.action;

import com.skysport.core.annotation.SystemControllerLog;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.login.service.ILoginService;
import com.skysport.inerfaces.constant.WebConstants;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Scope("prototype")
@Controller
@RequestMapping("/")
public class LoginAction {

    @Resource(name = "loginService")
    private ILoginService loginService;

    /**
     * 1，用户名，如果session存在该用户，执行2，否则执行3
     * 2，检验用户是否锁定，如果没有锁定，返回6，锁定返回5；
     * 3，用用户名查询用户信息，如果有该用户，执行4，否则执行5
     * 4，校验密码，如果密码正确，执行2，如果密码错误，执行5
     * 5，返回登录页面
     * 6，返回成功
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    @SystemControllerLog(description = "用户登录")
    public ModelAndView login(UserInfo user) throws Exception {
        return loginService.login(user);

    }

    @RequestMapping(value = "/loginout")
    @ResponseBody
    @SystemControllerLog(description = "用户登出")
    public ModelAndView loginout(HttpServletRequest request) throws Exception {
        request.getSession().removeAttribute(WebConstants.CURRENT_USER);
        return new ModelAndView("login");
    }

}
