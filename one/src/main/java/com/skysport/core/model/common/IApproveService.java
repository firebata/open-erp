package com.skysport.core.model.common;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/6.
 */
public interface IApproveService {
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

    /**
     * 提交
     *
     * @param businessKey
     */
    void submit(String businessKey);


}
