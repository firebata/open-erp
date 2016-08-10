package com.skysport.interfaces.bean.info;

import com.skysport.core.bean.system.SelectItem;

/**
 * 类说明:透湿（高透、中透、低透、不透气）
 * Created by zhangjh on 2015/6/25.
 */
public class WaterVapourPermeabilityInfo extends SelectItem {
    private String id;
    private String wvpId;
    private String wvpName;
    private int levelId;
    private int delFlag;
    private String updateTime;
    private String remark;


    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
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
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWvpId() {
        return wvpId;
    }

    public void setWvpId(String wvpId) {
        this.wvpId = wvpId;
    }

    public String getWvpName() {
        return wvpName;
    }

    public void setWvpName(String wvpName) {
        this.wvpName = wvpName;
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
