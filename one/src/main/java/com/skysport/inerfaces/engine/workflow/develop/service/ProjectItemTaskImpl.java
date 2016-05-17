package com.skysport.inerfaces.engine.workflow.develop.service;
import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.develop.ProjectItemMapper;
import com.skysport.inerfaces.model.develop.project.service.IProjectItemService;
import com.skysport.inerfaces.model.permission.userinfo.service.IStaffService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.HashedMap;
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
    @Resource(name = "projectItemMapper")
    private ProjectItemMapper projectItemMapper;

    @Resource
    private IStaffService developStaffImpl;

    @Resource(name = "projectItemManageService")
    private IProjectItemService projectItemManageService;

    @Override
    public void afterPropertiesSet() {
        approveMapper = projectItemMapper;
    }


    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey, String businessName) {


        Map<String, Object> variables = new HashMap<String, Object>();
        UserInfo userInfo = UserUtils.getUserFromSession();
        ProcessInstance processInstance = null;
        try {
            String userId = userInfo.getNatrualkey();
            identityService.setAuthenticatedUserId(userId);
            String groupIdDevManager = developStaffImpl.getManagerStaffGroupId();
            String groupIdDev = developStaffImpl.getStaffGroupId();
            variables.put(WebConstants.DEVLOP_STAFF_GROUP, groupIdDev);
            variables.put(WebConstants.DEVLOP_MANAGER, groupIdDevManager);
            variables.put(WebConstants.PROJECT_ITEM_ID, businessKey);
            variables.put(WebConstants.BUSINESS_NAME, businessName);
            processInstance = runtimeService.startProcessInstanceByKey(WebConstants.PROJECT_ITEM_PROCESS, businessKey, variables);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        return processInstance;
    }


    /**
     * 子项目提交审核
     *
     * @param businessKey
     */
    @Override
    public void submit(String businessKey) {
        //状态改为待审批
        updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_UNDO);
    }

    @Override
    public void submit(String taskId, String businessKey) {
        if (!WebConstants.NULL_STR.equals(taskId.trim())) {
            //完成当前任务
            Map<String, Object> variables = new HashMap<String, Object>();
            String groupIdDevManager = developStaffImpl.getManagerStaffGroupId();
            variables.put(WebConstants.DEVLOP_MANAGER, groupIdDevManager);
            complete(taskId, variables);
        }
        //状态改为待审批
        updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_UNDO);
    }


    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve, Task task) {
        Map<String, Object> variables = new HashedMap();
        String handleUserId;
        if (approve) {
            handleUserId = developStaffImpl.getManagerStaffGroupId();
            variables.put(WebConstants.DEVLOP_MANAGER, handleUserId);
        } else {
//            handleUserId = developStaffImpl.staffId();
//            variables.put(WebConstants.DEVLOP_STAFF, handleUserId);
        }
        variables.put(WebConstants.PROJECT_ITEM_PASS, approve);

        return variables;
    }

    @Override
    public <T> T invokePass(String businessKey) {
        return (T) projectItemManageService.createBoms(businessKey);
    }

    @Override
    public <T> T invokeReject(String businessKeys) {
        return null;
    }


}
