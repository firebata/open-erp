package com.skysport.inerfaces.engine.workflow.develop.service;
import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.inerfaces.mapper.develop.ProductionInstructionMapper;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016-05-12.
 */
@Service
public class ProductionInstructionTaskImpl extends WorkFlowServiceImpl {

    @Autowired
    private ProductionInstructionMapper productionInstructionMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        approveMapper = productionInstructionMapper;
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


}
