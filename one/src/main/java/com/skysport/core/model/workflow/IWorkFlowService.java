package com.skysport.core.model.workflow;

import com.skysport.inerfaces.bean.task.TaskVo;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/22.
 */
public interface IWorkFlowService {
    ProcessInstance startProcessInstanceByBussKey(String businessKey);

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey);

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);

    ProcessInstance startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);

    ProcessInstance startProcessInstanceById(String processDefinitionId);

    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey);


    ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables);

    ProcessInstance startProcessInstanceById(String processDefinitionId, String businessKey, Map<String, Object> variables);

    ProcessInstance startProcessInstanceByMessage(String messageName);

    List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String businessKey);


    /**
     * 查询待办
     *
     * @param userId
     * @return
     */
    List<TaskVo> queryToDoTask(String userId) throws InvocationTargetException, IllegalAccessException;

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
     * 查询结束的实例
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    List<HistoricProcessInstance> findFinishedProcessInstaces(int firstResult, int maxResults, String processDefinitionKey);

    /**
     * 根据流程实例id查询任务id（查询任务id对应的业务信息）
     *
     * @param processInStanceId 流程实例id
     * @return 业务主键
     */
    String queryBusinessKeyByProcessInstanceId(String processInStanceId);

    /**
     * 签收任务
     *
     * @param taskId 任务id
     */
    void claim(String taskId);

    List<ProcessInstance> queryProcessInstancesSuspendedByBusinessKey(String natrualKey);
}
