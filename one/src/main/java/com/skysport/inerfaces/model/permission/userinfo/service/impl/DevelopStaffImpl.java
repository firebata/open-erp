package com.skysport.inerfaces.model.permission.userinfo.service.impl;

import com.skysport.core.constant.CharConstant;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.model.permission.userinfo.service.IStaffService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.impl.identity.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 说明:
 * Created by zhangjh on 2016/4/10.
 */
@Service
public class DevelopStaffImpl implements IStaffService {
    @Resource
    IdentityService identityService;

    @Override
    public String getCommonStaffGroupId() {
        String authenticatedUserId = Authentication.getAuthenticatedUserId();
        return identityService.createGroupQuery().groupMember(authenticatedUserId).singleResult().getId();
    }

    @Override
    public String getManagerStaffGroupId() {
        return identityService.createGroupQuery().groupName("开发部经理").singleResult().getId();
    }


    @Override
    public String getParentGroupId(String groupId) {
        return CharConstant.EMPTY;
    }

    @Override
    public String staffId() {
        String authenticatedUserId = Authentication.getAuthenticatedUserId();
        return authenticatedUserId;
    }

    @Override
    public String managerId() {
        return UserUtils.getUserFromSession().getNatrualkey();
    }

}
