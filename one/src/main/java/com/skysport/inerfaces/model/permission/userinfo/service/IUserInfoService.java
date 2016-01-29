package com.skysport.inerfaces.model.permission.userinfo.service;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.common.ICommonService;

/**
 * 说明:
 * Created by zhangjh on 2015/8/18.
 */
public interface IUserInfoService extends ICommonService<UserInfo> {

    void chgpwd(UserInfo userInfo);
}
