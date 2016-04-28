package com.skysport.inerfaces.model.permission.roleinfo.service.impl;

import com.skysport.core.bean.permission.RoleInfo;
import com.skysport.core.bean.permission.RoleUser;
import com.skysport.inerfaces.mapper.permission.RoleInfoMapper;
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

    @Resource(name = "roleInfoMapper")
    private RoleInfoMapper roleInfoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = roleInfoMapper;
    }

    @Override
    public List<RoleUser> queryRoleUsers(String userId) {
        return roleInfoMapper.queryRoleUsers(userId);
    }

    @Override
    public String queryParentId(String groupId) {
        return roleInfoMapper.queryParentId(groupId);
    }

}
