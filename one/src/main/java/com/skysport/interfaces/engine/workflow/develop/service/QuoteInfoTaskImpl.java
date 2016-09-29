package com.skysport.interfaces.engine.workflow.develop.service;

import com.skysport.core.model.workflow.impl.WorkFlowServiceImpl;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.develop.QuotedInfoMapper;
import com.skysport.interfaces.mapper.info.BomInfoMapper;
import com.skysport.interfaces.model.develop.bom.IBomService;
import org.activiti.engine.task.Task;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2016-05-09.
 */
@Service
public class QuoteInfoTaskImpl extends WorkFlowServiceImpl {
    @Resource(name = "quotedInfoMapper")
    private QuotedInfoMapper quotedInfoMapper;

    @Resource(name = "bomManageService")
    private IBomService bomManageService;

    @Resource(name = "bomInfoMapper")
    private BomInfoMapper bomInfoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        approveMapper = quotedInfoMapper;
    }


    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve, Task task) {
        Map<String, Object> variables = new HashedMap();
        variables.put(WebConstants.SINGLE_QUOTE_PASS, approve);
        return variables;
    }

    @Override
    public String queryBusinessName(String businessKey) {
        return bomManageService.queryBusinessName(businessKey) + "预报价";
    }


    @Override
    public void updateApproveStatus(String businessKey, String status) {
        if (WebConstants.APPROVE_STATUS_REJECT.equals(status)) {
            bomInfoMapper.updateApproveStatus(businessKey, status);
        }
        quotedInfoMapper.updateApproveStatus(businessKey, status);
    }

    @Override
    public void updateApproveStatusBatch(List<String> businessKeys, String status) {
        quotedInfoMapper.updateApproveStatusBatch(businessKeys, status);
    }

    @Override
    public void submit(String businessKey) {
        //状态改为待审批
        updateApproveStatus(businessKey, WebConstants.APPROVE_STATUS_UNDO);
    }
}
