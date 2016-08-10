package com.skysport.interfaces.bean.jc;

import com.skysport.core.bean.system.SelectItem;

/**
 * 解封水压
 * Created by zhangjh on 2016-7-11 11:54:27
 */
public class JcSeamWaterpressure extends SelectItem {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String waterpressureId;
    /**
     *
     */
    private String name;
    /**
     * 0:正常；1:已删除
     */
    private int delFlag;
    /**
     *
     */
    private String createTime;
    /**
     *
     */
    private String updateTime;
    /**
     *
     */
    private String remark;

    @Override
    public String getId() {
        return id;
    }

    public String getWaterpressureId() {
        return waterpressureId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getDelFlag() {
        return delFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    @Override
    public String getUpdateTime() {
        return updateTime;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public void setWaterpressureId(String waterpressureId) {
        this.waterpressureId = waterpressureId;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
