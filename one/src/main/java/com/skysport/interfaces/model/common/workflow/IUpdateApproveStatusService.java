package com.skysport.interfaces.model.common.workflow;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016-05-12.
 */
public interface IUpdateApproveStatusService {
    void updateApproveStatus(String businessKey, String status);

    void updateApproveStatusBatch(List<String> businessKeys, String status);
}
