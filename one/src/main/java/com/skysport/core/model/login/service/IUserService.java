package com.skysport.core.model.login.service;

import com.skysport.core.bean.permission.UserInfo;

/**
 * 此类描述的是：
 *
 * @author: zhangjh
 * @version: 2015年4月30日 上午9:38:22
 */
public interface IUserService {

    UserInfo queryInfoByUserInfoName(String userName);

    UserInfo queryInfo(String userId);
}
