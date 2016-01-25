package com.skysport.core.model.login.helper;


import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.utils.SecurityUtil;
import com.skysport.inerfaces.constant.WebConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 说明:
 * Created by zhangjh on 2015/12/3.
 */
public class UserInfoServiceHelper {
    protected transient static Log logger = LogFactory.getLog(UserInfoServiceHelper.class);

    /**
     * 鉴定用户是否锁定
     *
     * @param userInDB
     * @return
     */
    public static boolean isUserUnlocked(UserInfo userInDB) {
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
    public static boolean isPwdRight(UserInfo userInDB, String password) throws Exception {
        String pwd = SecurityUtil.encrypt(password);
        logger.info("=========ecpwd======================>" + pwd);
        return pwd.equals(userInDB.getPassword());
    }


}
