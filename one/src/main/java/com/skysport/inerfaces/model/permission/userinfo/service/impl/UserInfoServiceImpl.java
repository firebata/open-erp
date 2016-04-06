package com.skysport.inerfaces.model.permission.userinfo.service.impl;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.inerfaces.mapper.permission.UserInfoManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.permission.userinfo.service.IUserInfoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 说明:
 * Created by zhangjh on 2015/8/18.
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends CommonServiceImpl<UserInfo> implements IUserInfoService, InitializingBean {

    @Resource(name = "userInfoManageMapper")
    private UserInfoManageMapper userInfoManageMapper;

    @Override
    public void afterPropertiesSet() {
        commonDao = userInfoManageMapper;
    }

    @Override
    public void chgpwd(UserInfo userInfo) {
        userInfoManageMapper.chgpwd(userInfo);
    }



}
