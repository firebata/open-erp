package com.skysport.core.model.workflow;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/22.
 */
public interface IWorkFlowService {

    void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);

    /**
     * 查询待办
     *
     * @param userId
     * @return
     */
    List<Task> queryToDoTask(String userId);

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
    List<HistoricProcessInstance> findFinishedProcessInstaces(int firstResult, int maxResults);

}
