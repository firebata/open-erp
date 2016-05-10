package com.skysport.core.model.workflow.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.workflow.IWorkFlowService;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.bean.form.task.TaskQueryForm;
import com.skysport.inerfaces.bean.task.TaskVo;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 说明:公用的任务处理
 * Created by zhangjh on 2015/12/22.
 */
@Service("workFlowServiceImpl")
public abstract class WorkFlowServiceImpl implements IWorkFlowService {
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
     * @param tasks
     * @return
     */
    public List<TaskVo> buildTaskVos(List<Task> tasks) {
        List<TaskVo> taskRtn = new ArrayList<>();

        if (null != tasks && !tasks.isEmpty()) {
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
        }
        return taskRtn;
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
     * @param businessKey
     * @return
     */
    @Override
    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String businessKey) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).active().orderByProcessInstanceId().desc();
        List<ProcessInstance> list = query.listPage(0, 100);
        return list;
    }

    /**
     * @param businessKey
     * @return
     */
    @Override
    public List<ProcessInstance> queryProcessInstancesSuspendedByBusinessKey(String businessKey) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).suspended().orderByProcessInstanceId().desc();
        List<ProcessInstance> list = query.listPage(0, 10);
        return list;
    }

    /**
     * @param businessKey
     * @return
     */
    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey) {
        return null;
    }

    /**
     * @param taskId 任务id
     */
    @Override
    public void claim(String taskId) {
        String userId = UserUtils.getUserFromSession().getNatrualkey();
        taskService.claim(taskId, userId);
    }

    /**
     * @param taskId
     * @param processInstanceId
     * @param message
     */
    @Override
    public void saveComment(String taskId, String processInstanceId, String message) {
        identityService.setAuthenticatedUserId(UserUtils.getUserFromSession().getNatrualkey());
        taskService.addComment(taskId, processInstanceId, message);
    }

    /**
     * @param taskId
     * @return
     */
    @Override
    public Task createTaskQueryByTaskId(String taskId) {
        return taskService.createTaskQuery().taskId(taskId).singleResult();
    }

    /**
     * @param processInstanceId
     * @return
     */
    @Override
    public List<Comment> getProcessInstanceComments(String processInstanceId) {
        return taskService.getProcessInstanceComments(processInstanceId);
    }

    /**
     * @param processInstanceId
     * @return
     */
    @Override
    public List<HistoricTaskInstance> createHistoricTaskInstanceQuery(String processInstanceId) {
        return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
    }

    /**
     * @param taskId
     * @param variables
     */
    @Override
    public void complete(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    /**
     * @param taskQueryForm
     * @param userId
     * @return
     */
    @Override
    public List<TaskVo> queryToDoTaskFiltered(TaskQueryForm taskQueryForm, String userId) {
        DataTablesInfo dataTablesInfo = taskQueryForm.getDataTablesInfo();
        int start = dataTablesInfo.getStart();
        int length = dataTablesInfo.getLength();
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.listPage(start, start + length);

        List<TaskVo> taskRtn = buildTaskVos(tasks);

        return taskRtn;
    }

    /**
     * 查询用户的待办任务总数
     *
     * @param userId
     * @return
     */
    @Override
    public long queryTaskTotal(String userId) {
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
        long totals = taskQuery.count();
        return totals;
    }

    /**
     * @param processInstanceId
     */
    public void suspendProcessInstanceById(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    public void suspendProcessInstanceById(List<ProcessInstance> instances) {
        for (ProcessInstance instance : instances) {
            suspendProcessInstanceById(instance.getProcessInstanceId());
        }
    }

    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(List<String> subtract) {
        List<ProcessInstance> totals = new ArrayList<>();
        for (String businessKey : subtract) {
            List<ProcessInstance> subs = queryProcessInstancesActiveByBusinessKey(businessKey);
            totals.addAll(subs);
        }
        return totals;
    }
}
