package com.skysport.inerfaces.bean.develop;

import com.skysport.core.bean.system.SelectItem;

/**
 * 物料信息
 * Created by zhangjh on 2015/6/23.
 */
public class MaterialInfo extends SelectItem {

    private String id;




    private int delFlag;
    private String remark;
    private String updateTime;

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
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
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
