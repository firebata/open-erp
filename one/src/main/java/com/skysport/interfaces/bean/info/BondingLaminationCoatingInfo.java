package com.skysport.interfaces.bean.info;

import com.skysport.core.bean.system.SelectItem;

/**
 * 类说明:是否复合或涂层（区别单布还是复合面料，若中间有膜或有涂层，则有下面8-12各级）
 * Created by zhangjh on 2015/6/25.
 */
public class BondingLaminationCoatingInfo extends SelectItem {
    private String id;
    private String blcId;
    private String blcName;
    private int levelId;
    private int delFlag;
    private String updateTime;
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBlcId() {
        return blcId;
    }

    public void setBlcId(String blcId) {
        this.blcId = blcId;
    }

    public String getBlcName() {
        return blcName;
    }

    public void setBlcName(String blcName) {
        this.blcName = blcName;
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
