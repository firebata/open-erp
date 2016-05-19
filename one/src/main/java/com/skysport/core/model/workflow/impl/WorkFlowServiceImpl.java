package com.skysport.core.model.workflow.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.exception.SkySportException;
import com.skysport.core.mapper.ApproveMapper;
import com.skysport.core.model.workflow.IApproveService;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.UserUtils;
import com.skysport.inerfaces.bean.form.task.TaskQueryForm;
import com.skysport.inerfaces.bean.task.ApproveVo;
import com.skysport.inerfaces.bean.task.TaskVo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.constant.develop.ReturnCodeConstant;
import com.skysport.inerfaces.engine.workflow.helper.TaskServiceHelper;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
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
public abstract class WorkFlowServiceImpl implements IApproveService, InitializingBean {
    protected transient Log logger = LogFactory.getLog(getClass());
    /**
     *
     */
    @Autowired
    public RepositoryService repositoryService;
    /**
     *
     */
    public ApproveMapper approveMapper;
    /**
     *
     */
    @Autowired
    public RuntimeService runtimeService;
    /**
     *
     */
    @Autowired
    public TaskService taskService;
    /**
     *
     */
    @Autowired
    public HistoryService historyService;
    /**
     *
     */
    @Autowired
    public IdentityService identityService;
    /**
     *
     */
    @Autowired
    public ManagementService managementService;


    @Override
    public void afterPropertiesSet() throws Exception {

    }


    /**
     * @param businessKey
     * @param status
     */
    @Override
    public void updateApproveStatus(String businessKey, String status) {
        approveMapper.updateApproveStatus(businessKey, status);
    }

    /**
     * @param businessKeys
     * @param status
     */
    @Override
    public void updateApproveStatusBatch(List<String> businessKeys, String status) {
        if (businessKeys != null && !businessKeys.isEmpty()) {
            approveMapper.updateApproveStatusBatch(businessKeys, status);
        }

    }

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
                String businessName = (String) task.getTaskLocalVariables().get(WebConstants.BUSINESS_NAME);
                String taskId = task.getId();
                String businessKey = processInstance.getBusinessKey();

                if (StringUtils.isBlank(businessKey)) {
                    continue;
                }
                if (StringUtils.isBlank(businessName)) {
                    businessName = getBusinessName(taskId, businessKey);
                }

                ProcessDefinition processDefinition = getProcessDefinition(processInstance.getProcessDefinitionId());
                int version = processDefinition.getVersion();
                String taskName = task.getName();
                String createTime = DateUtils.SINGLETONE.format(task.getCreateTime(), DateUtils.YYYY_MM_DD_HH_MM_SS);
                String assignee = task.getAssignee();
                boolean suspended = task.isSuspended();

                String processDefinitionId = processInstance.getProcessDefinitionId();


                TaskVo taskInfo = new TaskVo();
                taskInfo.setProcessInstanceId(processInstanceId);
                taskInfo.setBusinessKey(businessKey);
                taskInfo.setProcessDefinitionId(processDefinitionId);
                taskInfo.setId(taskId);
                taskInfo.setName(taskName);
                taskInfo.setCreateTime(createTime);
                taskInfo.setAssignee(assignee);
                taskInfo.setSuspended(suspended);
                taskInfo.setVersion(version);
                taskInfo.setBusinessName(businessName);
                taskRtn.add(taskInfo);
            }
        }
        return taskRtn;
    }

    public String getBusinessName(String taskId, String businessKey) {
        IApproveService approveService = TaskServiceHelper.getInstance().getIApproveService(taskId, this);
        String businessName = approveService.queryBusinessName(businessKey);
        return businessName;
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
    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String businessKey, String processDefinitionKey) {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(businessKey, processDefinitionKey).active().orderByProcessInstanceId().desc();
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
        List<ProcessInstance> list = query.listPage(0, 100);
        return list;
    }

    /**
     * @param businessKey
     * @return
     */
    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey) {
        List<ProcessInstance> list = queryProcessInstancesSuspendedByBusinessKey(businessKey);
        return list.get(0);
    }

    /**
     * 签收
     *
     * @param taskId 任务id
     */
    @Override
    public void claim(String taskId) {
        String userId = UserUtils.getUserFromSession().getNatrualkey();
        taskService.claim(taskId, userId);
    }

    /**
     * 反签收
     *
     * @param taskId 任务id
     */
    @Override
    public void unclaim(String taskId) {
        boolean canUnclaim = false;
        //反签收条件过滤
        List<IdentityLink> links = taskService.getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : links) {
            canUnclaim = StringUtils.equals(IdentityLinkType.CANDIDATE, identityLink.getType());
            if (canUnclaim) {
                taskService.claim(taskId, null);
                break;
            }
        }
        if (!canUnclaim) {
            throw new SkySportException(ReturnCodeConstant.UNCLAIM_TASK);
        }
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
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId).orderByTaskCreateTime().desc();
        List<Task> tasks = taskQuery.listPage(start, start + length);
        List<TaskVo> taskRtn = buildTaskVos(tasks);
        return taskRtn;
    }

    @Override
    public long listFilteredPantoneInfosCounts(TaskQueryForm taskQueryForm, String userId) {
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.list();
        return tasks.size();
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

    /**
     * @param instances
     */
    public void suspendProcessInstanceById(List<ProcessInstance> instances) {
        for (ProcessInstance instance : instances) {
            suspendProcessInstanceById(instance.getProcessInstanceId());
        }
    }

    /**
     * @param businessKeys
     * @return
     */
    @Override
    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(List<String> businessKeys) {
        List<ProcessInstance> totals = new ArrayList<>();
        for (String businessKey : businessKeys) {
            List<ProcessInstance> subs = queryProcessInstancesActiveByBusinessKey(businessKey);
            totals.addAll(subs);
        }
        return totals;
    }

    /**
     * @param businessKeys
     * @param processDefinitionKey
     * @return
     */
    @Override
    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(List<String> businessKeys, String processDefinitionKey) {
        List<ProcessInstance> totals = new ArrayList<>();
        for (String businessKey : businessKeys) {
            List<ProcessInstance> subs = queryProcessInstancesActiveByBusinessKey(businessKey, processDefinitionKey);
            totals.addAll(subs);
        }
        return totals;
    }

    /**
     * @param info
     * @param natrualKey
     */
    @Override
    public void setStatuCodeAlive(ApproveVo info, String natrualKey) {
        List<ProcessInstance> processInstancesActive = queryProcessInstancesActiveByBusinessKey(natrualKey);
        TaskServiceHelper.getInstance().setStatuCodeAlive(info, processInstancesActive);
    }

    /**
     * @param businessKey
     * @param taskId
     * @param processInstanceId
     * @param <T>
     * @return
     */
    @Override
    public <T> T invokePass(String businessKey, String taskId, String processInstanceId) {
        return null;
    }

    /**
     * @param businessKey
     * @param <T>
     * @return
     */
    @Override
    public <T> T invokePass(String businessKey) {
        return null;
    }

    /**
     * @param businessKeys
     * @param <T>
     * @return
     */
    @Override
    public <T> T invokeReject(String businessKeys) {
        return null;
    }

    public void suspendProcessInstanceByIds(List<String> subtract) {
        List<ProcessInstance> instances = queryProcessInstancesActiveByBusinessKey(subtract);
        suspendProcessInstanceById(instances);//终止流程
    }

    public void startWorkFlow(List<TaskVo> taskVos) {
        if (null != taskVos && !taskVos.isEmpty()) {
            for (TaskVo vo : taskVos) {
                String businessKey = vo.getBusinessKey();
                String businessName = vo.getBusinessName();
                updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_NEW);
                startProcessInstanceByBussKey(vo);
            }

        }

    }

    @Override
    public void startProcessInstanceByBussKey(TaskVo vo) {
        String businessKey = vo.getBusinessKey();
        String businessName = vo.getBusinessName();
        startProcessInstanceByBussKey(businessKey, businessName);
    }
}
