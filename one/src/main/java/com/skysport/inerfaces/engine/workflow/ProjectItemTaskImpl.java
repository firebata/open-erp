package com.skysport.inerfaces.engine.workflow;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.permission.roleinfo.service.IRoleInfoService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 说明:子项目任务处理
 * Created by zhangjh on 2016/4/1.
 */
@Service("projectItemTaskService")
public class ProjectItemTaskImpl extends TaskServiceImpl {

    @Autowired
    private IRoleInfoService roleInfoService;

    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        UserInfo userInfo = UserUtils.getUserFromSession();
        ProcessInstance processInstance = null;

        try {
            identityService.setAuthenticatedUserId(userInfo.getNatrualkey());
            processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

        } finally {
            identityService.setAuthenticatedUserId(null);

        }
        return processInstance;
    }

    @Override
    public void claim(String taskId) {
        String userId = UserUtils.getUserFromSession().getNatrualkey();
        taskService.claim(taskId, userId);
    }

    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey) {

//        ProjectBomInfo projectBomInfo = projectItemManageService.queryInfoByNatrualKey(businessKey);

        Map<String, Object> variables = new HashMap<String, Object>();
        UserInfo userInfo = UserUtils.getUserFromSession();
        ProcessInstance processInstance = null;
        try {
            String userId = userInfo.getNatrualkey();
            String groupId = identityService.createGroupQuery().groupMember(userId).list().get(0).getId();//开发人员所属组
            //开发经理的组id
            String groupIdDevManager = roleInfoService.queryParentId(groupId);

            identityService.setAuthenticatedUserId(userId);

            variables.put(WebConstants.DEVLOP_MANAGER, groupIdDevManager);
            processInstance = runtimeService.startProcessInstanceByKey(WebConstants.PROJECT_ITEM_PROCESS, businessKey, variables);

        } finally {
            identityService.setAuthenticatedUserId(null);

        }

        return processInstance;
    }
}
