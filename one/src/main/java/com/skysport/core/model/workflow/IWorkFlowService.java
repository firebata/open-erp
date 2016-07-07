package com.skysport.core.model.workflow;

import com.skysport.interfaces.bean.form.task.TaskQueryForm;
import com.skysport.interfaces.bean.task.ApproveVo;
import com.skysport.interfaces.bean.task.TaskVo;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/22.
 */
public interface IWorkFlowService {

    /**
     * @param businessKey
     * @return
     */
    ProcessInstance startProcessInstanceByBussKey(String businessKey);

    ProcessInstance startProcessInstanceByBussKey(String businessKey, String businessName);

    /**
     * @param businessKey
     * @return
     */
    List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String businessKey);

    /**
     * @param businessKey
     * @param processDefinitionKey
     * @return
     */
    List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String businessKey, String processDefinitionKey);

    /**
     * @param businessKeys
     * @return
     */
    List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(List<String> businessKeys);

    /**
     * @param businessKeys
     * @param processDefinitionKey
     * @return
     */
    List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(List<String> businessKeys, String processDefinitionKey);

    /**
     * 终止流程
     *
     * @param processInstanceId
     */
    void suspendProcessInstanceById(String processInstanceId);

    /**
     * 查询流程定义
     *
     * @param processDefinitionId
     * @return
     */
    ProcessDefinition getProcessDefinition(String processDefinitionId);


    /**
     * 签收任务
     *
     * @param taskId 任务id
     */
    void claim(String taskId);

    /**
     * @param natrualKey
     * @return
     */
    List<ProcessInstance> queryProcessInstancesSuspendedByBusinessKey(String natrualKey);

    /**
     * @param taskId
     * @param processInstanceId
     * @param message
     */
    void saveComment(String taskId, String processInstanceId, String message);

    /**
     * @param taskId
     * @return
     */
    Task createTaskQueryByTaskId(String taskId);

    /**
     * @param taskId
     * @param variables
     */
    void complete(String taskId, Map<String, Object> variables);

    /**
     * @param processInstanceId
     * @return
     */
    List<Comment> getProcessInstanceComments(String processInstanceId);

    /**
     * @param processInstanceId
     * @return
     */
    List<HistoricTaskInstance> createHistoricTaskInstanceQuery(String processInstanceId);

    /**
     * @param taskQueryForm
     * @param natrualkey
     * @return
     */
    List<TaskVo> queryToDoTaskFiltered(TaskQueryForm taskQueryForm, String natrualkey);

    /**
     * @param natrualkey
     * @return
     */
    long queryTaskTotal(String natrualkey);

    /**
     * @param instances
     */
    void suspendProcessInstanceById(List<ProcessInstance> instances);


    /**
     * @param info
     * @param natrualKey
     */
    void setStatuCodeAlive(ApproveVo info, String natrualKey);

    /**
     * 提交
     *
     * @param businessKey
     */
    void submit(String businessKey);

    /**
     * @param taskId
     * @param businessKey
     */
    void submit(String taskId, String businessKey);

    /**
     * @param approve
     * @param task
     * @return
     */
    Map<String, Object> getVariableOfTaskNeeding(boolean approve, Task task);

    /**
     * 审核通过的处理
     *
     * @param businessKey
     */
    <T> T invokePass(String businessKey);

    /**
     * @param businessKey
     * @param taskId
     * @param processInstanceId
     */
    <T> T invokePass(String businessKey, String taskId, String processInstanceId);

    /**
     * 审核通过的处理
     *
     * @param businessKey
     */
    <T> T invokeReject(String businessKey);

    void suspendProcessInstanceByIds(List<String> subtract);

    void startWorkFlow(List<TaskVo> taskVos);

    void startProcessInstanceByBussKey(TaskVo vo);

    String queryBusinessName(String businessKey);

    void unclaim(String taskId);

    long listFilteredPantoneInfosCounts(TaskQueryForm taskQueryForm, String natrualkey);
}
