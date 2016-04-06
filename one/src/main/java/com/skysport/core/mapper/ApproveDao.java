package com.skysport.core.mapper;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/4/6.
 */
public interface ApproveDao {
    /**
     * @param businessKey 业务主键
     * @param status      状态
     */
    void updateApproveStatus(String businessKey, String status);

    /**
     * @param businessKeys 业务主键
     * @param status       状态
     */
    void updateApproveStatusBatch(List<String> businessKeys, String status);
}
