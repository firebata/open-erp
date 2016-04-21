package com.skysport.inerfaces.mapper.permission;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.mapper.CommonDao;
import org.springframework.stereotype.Repository;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/17.
 */
@Repository("userInfoManageMapper")
public interface UserInfoManageMapper extends CommonDao<UserInfo> {

    UserInfo queryInfoByUserInfoName(String userName);

    void chgpwd(UserInfo userInfo);

    String queryParentId(String groupId);
}
