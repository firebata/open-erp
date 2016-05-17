package com.skysport.inerfaces.engine.workflow.develop.service;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.info.BomInfoMapper;
import com.skysport.inerfaces.model.permission.userinfo.service.IStaffService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016-05-09.
 */
@Service
public class BomInfoTaskImpl extends WorkFlowServiceImpl {
    @Resource(name = "bomInfoMapper")
    private BomInfoMapper bomInfoMapper;
    @Resource
    private IStaffService developStaffImpl;

    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey) {
        return startProcessInstanceByBussKey(businessKey, CharConstant.EMPTY);
    }

    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey, String businessName) {
        Map<String, Object> variables = new HashMap<String, Object>();
        UserInfo userInfo = UserUtils.getUserFromSession();
        ProcessInstance processInstance = null;
        try {
            String userId = userInfo.getNatrualkey();
            String groupIdDevManager = developStaffImpl.getManagerStaffGroupId();
            identityService.setAuthenticatedUserId(userId);
            variables.put(WebConstants.DEVLOP_MANAGER, groupIdDevManager);
            variables.put(WebConstants.BUSINESS_NAME, businessName);
            processInstance = runtimeService.startProcessInstanceByKey(WebConstants.APPROVE_BOM_PROCESS, businessKey, variables);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        return processInstance;
    }

    @Override
    public void updateApproveStatus(String businessKey, String status) {
        bomInfoMapper.updateApproveStatus(businessKey, status);
    }

    @Override
    public void updateApproveStatusBatch(List<String> businessKeys, String status) {
        bomInfoMapper.updateApproveStatusBatch(businessKeys, status);
    }

    @Override
    public void submit(String businessKey) {
        //启动流程
//        startWorkFlow(businessKey);
        //状态改为待审批
        updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_UNDO);
    }

    @Override
    public void submit(String taskId, String businessKey) {

        updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_UNDO);
    }


    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve, Task task) {
        String taskDefinitionKey = task.getTaskDefinitionKey();
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
        return null;
    }

    @Override
    public <T> T invokeReject(String businessKey) {
        return null;
    }



    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
