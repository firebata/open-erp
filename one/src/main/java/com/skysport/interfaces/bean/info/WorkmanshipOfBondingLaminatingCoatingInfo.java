package com.skysport.interfaces.bean.info;

import com.skysport.core.bean.system.SelectItem;

/**
 * 类说明:贴膜或涂层工艺（离型纸转移贴膜、干法涂层、湿法涂层，等等）
 * Created by zhangjh on 2015/6/25.
 */
public class WorkmanshipOfBondingLaminatingCoatingInfo extends SelectItem {
    private String id;
    private String woblcid;
    private String woblcName;
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


    public String getWoblcid() {
        return woblcid;
    }

    public void setWoblcid(String woblcid) {
        this.woblcid = woblcid;
    }

    public String getWoblcName() {
        return woblcName;
    }

    public void setWoblcName(String woblcName) {
        this.woblcName = woblcName;
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

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
