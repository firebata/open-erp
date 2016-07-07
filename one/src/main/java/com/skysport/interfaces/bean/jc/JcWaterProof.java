package com.skysport.interfaces.bean.jc;

import com.skysport.core.bean.system.SelectItem;

/**
 * 实体bean
 * Created by zhangjh on 2016-7-5 9:11:41
 */
public class JcWaterProof extends SelectItem {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String waterProofId;
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

    private String remark;

    /**
     *
     */
    public String getCreateTime() {
        return this.createTime;
    }

    /**
     *
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    /**
     *
     */
    public String getUpdateTime() {
        return this.updateTime;
    }

    /**
     *
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    /**
     *
     */
    public String getId() {
        return this.id;
    }

    /**
     *
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     */
    public String getWaterProofId() {
        return this.waterProofId;
    }

    /**
     *
     */
    public void setWaterProofId(String waterProofId) {
        this.waterProofId = waterProofId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     */
    public int getDelFlag() {
        return this.delFlag;
    }

    /**
     *
     */
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
