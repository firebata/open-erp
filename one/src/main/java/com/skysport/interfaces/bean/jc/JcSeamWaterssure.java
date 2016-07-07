package com.skysport.interfaces.bean.jc;

import com.skysport.core.bean.system.SelectItem;

/**
 * 实体bean
 * Created by zhangjh on 2016-7-7 15:10:10
 */
public class JcSeamWaterssure extends SelectItem {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String waterssureId;
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

    /**
     *
     */
    public String getWaterssureId() {
        return this.waterssureId;
    }

    /**
     *
     */
    public void setWaterssureId(String waterssureId) {
        this.waterssureId = waterssureId;
    }

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
    public String getName() {
        return this.name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
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
    public String getRemark() {
        return this.remark;
    }

    /**
     *
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
    public int getDelFlag() {
        return this.delFlag;
    }

    /**
     *
     */
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

}
