package com.skysport.inerfaces.engine.workflow;

import com.skysport.core.model.workflow.InstanceService;
import com.skysport.core.init.SkySportAppContext;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/22.
 */
@Service("devlopmentInstanceServiceImpl")
public class DevlopmentInstanceServiceImpl implements InstanceService {
    @Override
    public void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
        SkySportAppContext.runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
    }
}
