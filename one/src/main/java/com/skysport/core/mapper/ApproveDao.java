package com.skysport.core.mapper;

import org.apache.ibatis.annotations.Param;

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
    void updateApproveStatus(@Param(value = "businessKey") String businessKey, @Param(value = "status") String status);

    /**
     * @param businessKeys 业务主键
     * @param status       状态
     */
    void updateApproveStatusBatch(@Param(value = "businessKeys") List<String> businessKeys, @Param(value = "status") String status);
}
