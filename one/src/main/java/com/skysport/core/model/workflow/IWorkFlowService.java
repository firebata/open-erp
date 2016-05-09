package com.skysport.core.model.workflow;

import com.skysport.inerfaces.bean.form.task.TaskQueryForm;
import com.skysport.inerfaces.bean.task.TaskVo;
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

    ProcessInstance startProcessInstanceByBussKey(String businessKey);


    List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String businessKey);

    /**
     * 查询流程实例
     *
     * @param processInstanceId
     * @return
     */
    ProcessInstance queryProcessInstance(String processInstanceId);

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

    List<ProcessInstance> queryProcessInstancesSuspendedByBusinessKey(String natrualKey);

    void saveComment(String taskId, String processInstanceId, String message);

    Task createTaskQueryByTaskId(String taskId);

    void complete(String taskId, Map<String, Object> variables);

    List<Comment> getProcessInstanceComments(String processInstanceId);

    List<HistoricTaskInstance> createHistoricTaskInstanceQuery(String processInstanceId);

    List<TaskVo> queryToDoTaskFiltered(TaskQueryForm taskQueryForm, String natrualkey);

    long queryTaskTotal(String natrualkey);
}
