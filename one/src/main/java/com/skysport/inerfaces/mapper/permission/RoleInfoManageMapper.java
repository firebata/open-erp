package com.skysport.inerfaces.mapper.permission;

import com.skysport.core.bean.permission.RoleInfo;
import com.skysport.core.bean.permission.RoleUser;
import com.skysport.core.mapper.CommonDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/17.
 */
@Component("roleInfoManageMapper")
public interface RoleInfoManageMapper extends CommonDao<RoleInfo> {
    List<RoleUser> queryRoleUsers(String userId);

    String queryParentId(@Param(value = "groupId") String groupId);
}
