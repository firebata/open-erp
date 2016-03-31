package com.skysport.inerfaces.engine.workflow;

import com.skysport.core.init.SkySportAppContext;
import com.skysport.core.model.workflow.IWorkFlowService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/22.
 */
@Service("devlopmentTaskService")
public class DevlopmentTaskServiceImpl extends SkySportAppContext implements IWorkFlowService {


    @Override
    public void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
        runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }

    @Override
    public List<Task> queryToDoTask(String userId) {

        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned(userId);
        List<Task> tasks = taskQuery.list();
        return tasks;
    }

    @Override
    public ProcessInstance queryProcessInstance(String processInstanceId) {
        return null;
    }

    @Override
    public ProcessDefinition getProcessDefinition(String processDefinitionId) {
        return null;
    }

    @Override
    public List<HistoricProcessInstance> findFinishedProcessInstaces(int firstResult, int maxResults) {
        return null;
    }

}
