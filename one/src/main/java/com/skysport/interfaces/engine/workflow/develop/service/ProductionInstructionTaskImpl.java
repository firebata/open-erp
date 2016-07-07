package com.skysport.interfaces.engine.workflow.develop.service;

import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.develop.ProductionInstructionMapper;
import com.skysport.interfaces.model.develop.bom.IBomService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016-05-12.
 */
@Service
public class ProductionInstructionTaskImpl extends WorkFlowServiceImpl {

    @Resource(name = "bomManageService")
    private IBomService bomManageService;

    @Autowired
    private ProductionInstructionMapper productionInstructionMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        approveMapper = productionInstructionMapper;
    }


    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve, Task task) {
        Map<String, Object> variables = new HashedMap();
        variables.put(WebConstants.SINGLE_PRINTR_PASS, approve);
        return variables;
    }

    /**
     * @param businessKey
     */
    @Override
    public String queryBusinessName(String businessKey) {
        return bomManageService.queryBusinessName(businessKey) + "生产指示单";
    }

    /**
     * @param businessKey
     * @param status
     */
    @Override
    public void updateApproveStatus(String businessKey, String status) {
        productionInstructionMapper.updateApproveStatus(businessKey, status);
    }

    /**
     * @param businessKeys
     * @param status
     */
    @Override
    public void updateApproveStatusBatch(List<String> businessKeys, String status) {
        productionInstructionMapper.updateApproveStatusBatch(businessKeys, status);
    }

}
