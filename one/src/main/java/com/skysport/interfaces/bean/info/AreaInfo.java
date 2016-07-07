package com.skysport.interfaces.bean.info;

import com.skysport.core.bean.system.SelectItem;

/**
 * 区域表
 * Created by zhangjh on 2015/6/9.
 */
public class AreaInfo extends SelectItem {

    private String id;
    /**
     * 区域
     */
    private String areaId;
    /**
     * 关联客户信息
     */
    private String customerId;
    /**
     * 客户名称
     */
    private String customerName;

    private int delFlag;

    private String remark;

    private String updateTime;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }


    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
