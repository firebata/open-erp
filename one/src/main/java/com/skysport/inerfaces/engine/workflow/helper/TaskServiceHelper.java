package com.skysport.inerfaces.engine.workflow.helper;

import com.skysport.core.cache.TaskHanlderCachedMap;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.model.common.IApproveService;
import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.inerfaces.bean.task.ApproveVo;
import com.skysport.inerfaces.constant.WebConstants;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/10.
 */
public class TaskServiceHelper {
    private static TaskServiceHelper ourInstance = new TaskServiceHelper();

    public static TaskServiceHelper getInstance() {
        return ourInstance;
    }

    private TaskServiceHelper() {
    }

    /**
     * 校验是否含有运行中的流程实例
     *
     * @param processInstancesActives
     * @return
     */
    public boolean isInstanceAlive(List<ProcessInstance>... processInstancesActives) {
        boolean isActive = false;
        for (List<ProcessInstance> instances : processInstancesActives) {
            isActive = instances != null && !instances.isEmpty();
            if (isActive) {
                break;
            }
        }
        return isActive;
    }

    /**
     * 为业务对象保存 “实例状态运行状态”
     *
     * @param info
     * @param businessService
     * @param natrualKey
     */
    public void setStatuCodeAlive(ApproveVo info, IApproveService businessService, String natrualKey) {
        boolean isActive = isActive(businessService, natrualKey);
        if (isActive) {
            info.setStateCode(WebConstants.STATECODE_ALIVE);
        }
    }

    public boolean isActive(IApproveService businessService, String natrualKey) {
        List<ProcessInstance> processInstancesActive = businessService.queryProcessInstancesActiveByBusinessKey(natrualKey);
        List<ProcessInstance> processInstancesSuspended = businessService.queryProcessInstancesSuspendedByBusinessKey(natrualKey);
        return TaskServiceHelper.getInstance().isInstanceAlive(processInstancesActive, processInstancesSuspended);
    }

    /**
     * 获取相信业务处理的对象
     *
     * @param taskId
     * @param taskServiceImpl
     * @return
     */
    public IApproveService getIApproveService(String taskId, IWorkFlowService taskServiceImpl) {
        Task task = taskServiceImpl.createTaskQueryByTaskId(taskId);
        String taskDefinitionKey = task.getTaskDefinitionKey();
        String beanName = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getBusinessService();
        return (IApproveService) SpringContextHolder.getBean(beanName);
    }
}
