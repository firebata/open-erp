package com.skysport.interfaces.mapper.permission;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.mapper.CommonMapper;
import org.springframework.stereotype.Repository;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/17.
 */
@Repository("userInfoMapper")
public interface UserInfoMapper extends CommonMapper<UserInfo> {

    UserInfo queryInfoByUserInfoName(String userName);

    void chgpwd(UserInfo userInfo);

    String queryParentId(String groupId);
}
