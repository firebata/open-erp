package com.skysport.core.model.login.helper;


import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.utils.SecurityUtil;
import com.skysport.inerfaces.constant.WebConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/3.
 */
public enum UserInfoServiceHelper {
    SINGLETONE;
    protected transient static Log logger = LogFactory.getLog(UserInfoServiceHelper.class);

    /**
     * 鉴定用户是否锁定
     *
     * @param userInDB
     * @return
     */
    public boolean isUserUnlocked(UserInfo userInDB) {
        return WebConstants.USER_IS_UNLOCK == Integer.parseInt(userInDB.getLockFlag());
    }

    /**
     * 鉴定用户密码是否正确
     *
     * @param userInDB
     * @param password
     * @return
     * @throws Exception
     */
    public boolean isPwdRight(UserInfo userInDB, String password) throws Exception {
        String pwd = SecurityUtil.encrypt(password);
        return pwd.equals(userInDB.getPassword());
    }


    public ModelAndView toLoginPage() {
        return new ModelAndView("login");
    }


    public ModelAndView toMainPage(UserInfo userInfo) {
        ModelAndView mav = new ModelAndView("main");
        Map<String, Object> result = new HashMap<>();
        result.put("username", userInfo.getAliases());
        mav.addAllObjects(result);
        return mav;
    }
}
