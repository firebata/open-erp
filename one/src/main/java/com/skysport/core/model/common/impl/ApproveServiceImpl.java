package com.skysport.core.model.common.impl;

import com.skysport.core.model.common.IApproveService;

/**
 * 说明:
 * Created by zhangjh on 2016-05-09.
 */
public abstract class ApproveServiceImpl implements IApproveService {
    /**
     * 审核通过的处理
     *
     * @param businessKeys
     */
    public void invokePass(String businessKey) {

    }

    /**
     * 审核通过的处理
     *
     * @param businessKeys
     */
    public void invokeReject(String businessKey) {

    }
}
