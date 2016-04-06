package com.skysport.inerfaces.model.permission.roleinfo.service.impl;

import com.skysport.core.bean.permission.RoleInfo;
import com.skysport.core.bean.permission.RoleUser;
import com.skysport.inerfaces.mapper.permission.RoleInfoManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.permission.roleinfo.service.IRoleInfoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/28.
 */
@Service("roleInfoService")
public class RoleInfoServiceImpl extends CommonServiceImpl<RoleInfo> implements IRoleInfoService, InitializingBean {

    @Resource(name = "roleInfoManageMapper")
    private RoleInfoManageMapper roleInfoManageMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = roleInfoManageMapper;
    }

    @Override
    public List<RoleUser> queryRoleUsers(String userId) {
        return roleInfoManageMapper.queryRoleUsers(userId);
    }

    @Override
    public String queryParentId(String groupId) {
        return roleInfoManageMapper.queryParentId(groupId);
    }

}
