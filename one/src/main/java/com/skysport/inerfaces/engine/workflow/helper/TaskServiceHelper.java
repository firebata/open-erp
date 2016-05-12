package com.skysport.inerfaces.engine.workflow.helper;

import com.skysport.core.cache.TaskHanlderCachedMap;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.model.workflow.IApproveService;
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

    private TaskServiceHelper() {
    }

    public static TaskServiceHelper getInstance() {
        return ourInstance;
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
     * @param processInstancesActive
     */
    public void setStatuCodeAlive(ApproveVo info, List<ProcessInstance> processInstancesActive) {
        boolean isActive = isActive(processInstancesActive);
        if (isActive) {
            info.setStateCode(WebConstants.STATECODE_ALIVE);
        }
    }

    public boolean isActive(List<ProcessInstance> processInstancesActive) {
        return TaskServiceHelper.getInstance().isInstanceAlive(processInstancesActive);
    }

    /**
     * 获取相信业务处理的对象
     *
     * @param taskId
     * @param taskServiceImpl
     * @return
     */
    public IApproveService getIApproveService(String taskId, IWorkFlowService taskServiceImpl) {
        String taskDefinitionKey = getTaskDefinitionKey(taskId, taskServiceImpl);
        return getApproveService(taskDefinitionKey);
    }

    /**
     * @param taskId
     * @param taskServiceImpl
     * @return
     */
    public String getTaskDefinitionKey(String taskId, IWorkFlowService taskServiceImpl) {
        Task task = taskServiceImpl.createTaskQueryByTaskId(taskId);
        return task.getTaskDefinitionKey();
    }

    /**
     * @param taskDefinitionKey
     * @return
     */
    public IApproveService getApproveService(String taskDefinitionKey) {
        String taskService = TaskHanlderCachedMap.SINGLETONE.queryValue(taskDefinitionKey).getTaskService();
        IApproveService approveService = SpringContextHolder.getBean(taskService);
        return approveService;
    }
}
