package com.skysport.interfaces.engine.workflow.helper;

import com.skysport.core.bean.system.SelectItem;
import com.skysport.core.cache.TaskHanlderCachedMap;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.model.workflow.IApproveService;
import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.interfaces.bean.task.ApproveVo;
import com.skysport.interfaces.bean.task.TaskVo;
import com.skysport.interfaces.constant.WebConstants;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.ArrayList;
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

    /**
     * 在更新的bom集合中找到需要启动的bom集合
     *
     * @param instancesIntersection 运行中的BOM实例
     * @param needUpdateIdList      需要更新的bom集合
     * @return 更新的bom集合中需要启动的bom集合
     */
    public List<String> chooseNeedToStartInUpdates(List<ProcessInstance> instancesIntersection, List<String> needUpdateIdList) {
        List<String> results = needUpdateIdList;
        if (null != instancesIntersection && !instancesIntersection.isEmpty()) {
            for (ProcessInstance pr : instancesIntersection) {
                results.remove(pr.getBusinessKey());
            }
        }
        return results;
    }

    /**
     * @param datas
     * @return
     */
    public List<TaskVo> changeToBusinessVo(List<? extends SelectItem> datas) {
        List<TaskVo> taskVos = new ArrayList<>();
        if (null != datas && !datas.isEmpty()) {
            for (SelectItem item : datas) {
                TaskVo vo = new TaskVo();
                vo.setBusinessKey(item.getNatrualkey());
                vo.setBusinessName(item.getName());
                taskVos.add(vo);
            }
        }
        return taskVos;

    }
}
