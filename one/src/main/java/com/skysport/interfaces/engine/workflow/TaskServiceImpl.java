package com.skysport.interfaces.engine.workflow;

import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.interfaces.bean.task.TaskVo;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016/4/10.
 */
@Service
public class TaskServiceImpl extends WorkFlowServiceImpl {

    @Override
    public ProcessInstance startProcessInstanceByBussKey(String businessKey, String businessName) {
        return null;
    }

    @Override
    public void submit(String businessKey) {

    }

    @Override
    public void submit(String taskId, String businessKey) {

    }

    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve, Task task) {
        return null;
    }

    @Override
    public void startProcessInstanceByBussKey(TaskVo vo) {

    }

    @Override
    public String queryBusinessName(String businessKey) {
        return null;
    }


}
