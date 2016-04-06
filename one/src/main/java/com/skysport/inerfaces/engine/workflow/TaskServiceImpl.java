package com.skysport.inerfaces.engine.workflow;

import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.core.utils.DateUtils;
import com.skysport.inerfaces.bean.task.TaskVo;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 说明:公用的任务处理
 * Created by zhangjh on 2015/12/22.
 */
@Service("taskService")
public abstract class TaskServiceImpl implements IWorkFlowService {
    @Autowired
    public RepositoryService repositoryService;

    @Autowired
    public RuntimeService runtimeService;

    @Autowired
    public TaskService taskService;

    @Autowired
    public HistoryService historyService;

    @Autowired
    public IdentityService identityService;

    @Autowired
    public ManagementService managementService;

    /**
     * 启动流程
     *
     * @param processDefinitionKey 流程定义主键
     * @return
     */
    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey);
    }

    /**
     * @param processDefinitionKey
     * @param variables
     * @return
     */
    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }

    @Override
    public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);
    }

    @Override
    public ProcessInstance startProcessInstanceById(String processDefinitionId) {
        return runtimeService.startProcessInstanceById(processDefinitionId);
    }

    @Override
    public ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey) {
        return runtimeService.startProcessInstanceById(processDefinitionId, businessKey);
    }

    @Override
    public ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceById(processDefinitionId, variables);
    }

    @Override
    public ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables) {
        return runtimeService.startProcessInstanceById(processDefinitionId, businessKey, variables);
    }

    @Override
    public ProcessInstance startProcessInstanceByMessage(String messageName) {
        return runtimeService.startProcessInstanceByMessage(messageName);
    }

    /**
     * 查询待办任务
     *
     * @param userId
     * @return
     */
    @Override
    public List<TaskVo> queryToDoTask(String userId) throws InvocationTargetException, IllegalAccessException {
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.list();
        List<TaskVo> taskRtn = new ArrayList<>();
        // 根据流程的业务ID查询实体并关联
        for (Task task : tasks) {
            String processInstanceId = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
            String businessKey = processInstance.getBusinessKey();
            String processDefinitionId = processInstance.getProcessDefinitionId();
            ProcessDefinition processDefinition = getProcessDefinition(processInstance.getProcessDefinitionId());
            if (businessKey == null) {
                continue;
            }
            TaskVo taskInfo = new TaskVo();
            String taskId = task.getId();
            String taskName = task.getName();
            String createTime = DateUtils.SINGLETONE.format(task.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
            String assignee = task.getAssignee();
            boolean suspended = task.isSuspended();
            int version = processDefinition.getVersion();
            taskInfo.setProcessInstanceId(processInstanceId);
            taskInfo.setBusinessKey(businessKey);
            taskInfo.setProcessDefinitionId(processDefinitionId);
            taskInfo.setId(taskId);
            taskInfo.setName(taskName);
            taskInfo.setCreateTime(createTime);
            taskInfo.setAssignee(assignee);
            taskInfo.setSuspended(suspended);
            taskInfo.setVersion(version);
            taskRtn.add(taskInfo);
        }
        return taskRtn;
    }


    /**
     * 查询流程实例
     *
     * @param processInstanceId
     * @return
     */
    @Override
    public ProcessInstance queryProcessInstance(String processInstanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();
        return processInstance;
    }

    /**
     * 查询流程定义
     *
     * @param processDefinitionId
     * @return
     */
    @Override
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }

    /**
     * 查询历史流程定义
     *
     * @param firstResult
     * @param maxResults
     * @param processDefinitionKey
     * @return
     */
    @Override
    public List<HistoricProcessInstance> findFinishedProcessInstaces(int firstResult, int maxResults, String processDefinitionKey) {

        HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().processDefinitionKey("leave").finished().orderByProcessInstanceEndTime().desc();
        List<HistoricProcessInstance> list = query.listPage(firstResult, maxResults);
        return list;
    }

    /**
     * 查询业务逐渐
     *
     * @param processInStanceId 流程实例id
     * @return
     */
    @Override
    public String queryBusinessKeyByProcessInstanceId(String processInStanceId) {
        ProcessInstance processInstance = queryProcessInstance(processInStanceId);
        String businessKey = processInstance.getBusinessKey();
        return businessKey;
    }

}
