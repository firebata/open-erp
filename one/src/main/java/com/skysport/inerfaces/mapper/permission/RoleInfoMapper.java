package com.skysport.inerfaces.mapper.permission;

import com.skysport.core.bean.permission.RoleInfo;
import com.skysport.core.bean.permission.RoleUser;
import com.skysport.core.mapper.CommonDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/17.
 */
@Repository("roleInfoMapper")
public interface RoleInfoMapper extends CommonDao<RoleInfo> {
    List<RoleUser> queryRoleUsers(String userId);

    String queryParentId(@Param(value = "groupId") String groupId);
}
