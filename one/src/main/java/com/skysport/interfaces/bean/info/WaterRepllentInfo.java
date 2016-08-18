package com.skysport.interfaces.bean.info;

import com.skysport.core.bean.system.SelectItem;

/**
 * 类说明:防泼水（所添加的功能性助剂）
 * Created by zhangjh on 2015/6/25.
 */
public class WaterRepllentInfo extends SelectItem {
    private String id;
    private String waterRepllentId;
    private String finishName;
    private int levelId;
    private int delFlag;
    private String updateTime;
    private String remark;

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

    public String getWaterRepllentId() {
        return waterRepllentId;
    }

    public void setWaterRepllentId(String waterRepllentId) {
        this.waterRepllentId = waterRepllentId;
    }

    public String getFinishName() {
        return finishName;
    }

    public void setFinishName(String finishName) {
        this.finishName = finishName;
    }
}
