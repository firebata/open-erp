package com.skysport.core.model.login.service;

import com.skysport.core.bean.permission.UserInfo;
import org.springframework.web.servlet.ModelAndView;

/**
 * 说明:
 * Created by zhangjh on 2015/12/2.
 */
public interface ILoginService {
    ModelAndView login(UserInfo user) throws Exception;
}
