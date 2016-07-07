package com.skysport.interfaces.bean.jc;

import com.skysport.core.bean.system.SelectItem;

/**
 * 实体bean
 * Created by zhangjh on 2016-7-7 13:32:34
 */
public class JcAntMosquitos extends SelectItem {
    /**
     *
     */
    private String id;
    /**
     *
     */
    private String antMosquitosId;
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

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getAntMosquitosId() {
        return antMosquitosId;
    }

    public void setAntMosquitosId(String antMosquitosId) {
        this.antMosquitosId = antMosquitosId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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
