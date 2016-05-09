package com.skysport.inerfaces.engine.workflow.develop.service;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.permission.userinfo.service.IStaffService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明:子项目任务处理
 * Created by zhangjh on 2016/4/1.
 */
@Service("projectItemTaskService")
public class ProjectItemTaskImpl extends WorkFlowServiceImpl {

    @Resource
    private IStaffService developStaffImpl;

    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey) {

        Map<String, Object> variables = new HashMap<String, Object>();
        UserInfo userInfo = UserUtils.getUserFromSession();
        ProcessInstance processInstance = null;
        try {
            String userId = userInfo.getNatrualkey();
            String groupIdDevManager = developStaffImpl.getManagerStaffGroupId();
            identityService.setAuthenticatedUserId(userId);
            variables.put(WebConstants.DEVLOP_MANAGER, groupIdDevManager);
            processInstance = runtimeService.startProcessInstanceByKey(WebConstants.PROJECT_ITEM_PROCESS, businessKey, variables);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        return processInstance;
    }
}
