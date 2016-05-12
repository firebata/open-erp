package com.skysport.inerfaces.engine.workflow;

import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
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
    public void submit(String businessKey) {

    }

    @Override
    public void submit(String taskId, String businessKey) {

    }

    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve, Task task) {
        return null;
    }


}
