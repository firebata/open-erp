package com.skysport.core.model.workflow;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/6.
 */
public interface IApproveService extends IWorkFlowService {
    /**
     * 更改审核状态
     *
     * @param businessKey
     * @param status
     */
    void updateApproveStatus(String businessKey, String status);

    /**
     * 批量更改审核状态
     *
     * @param businessKeys
     * @param status
     */
    void updateApproveStatusBatch(List<String> businessKeys, String status);


}
