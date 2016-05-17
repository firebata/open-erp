package com.skysport.inerfaces.engine.workflow.develop.service;

import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.inerfaces.mapper.develop.QuotedInfoMapper;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016-05-09.
 */
@Service
public class QuoteInfoTaskImpl extends WorkFlowServiceImpl {
    @Resource(name = "quotedInfoMapper")
    private QuotedInfoMapper quotedInfoMapper;


    @Override
    public void afterPropertiesSet() throws Exception {
        approveMapper = quotedInfoMapper;
    }

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

}
