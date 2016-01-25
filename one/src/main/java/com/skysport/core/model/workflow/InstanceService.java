package com.skysport.core.model.workflow;

import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/12/22.
 */
public interface InstanceService {

    public void startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables);

}
