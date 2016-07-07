package com.skysport.interfaces.model.permission.roleinfo.service;

import com.skysport.core.bean.permission.RoleInfo;
import com.skysport.core.bean.permission.RoleUser;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/28.
 */
public interface IRoleInfoService extends ICommonService<RoleInfo> {

    List<RoleUser> queryRoleUsers(String userId);

    String queryParentId(String groupId);

}
