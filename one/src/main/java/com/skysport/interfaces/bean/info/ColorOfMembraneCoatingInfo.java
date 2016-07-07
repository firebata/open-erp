package com.skysport.interfaces.bean.info;

import com.skysport.core.bean.system.SelectItem;

/**
 * 类说明:膜或涂层的颜色 （透明、白、彩色、印花，等等）
 * Created by zhangjh on 2015/6/25.
 */
public class ColorOfMembraneCoatingInfo extends SelectItem {
    private String id;
    private String comcId;
    private String comcName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComcId() {
        return comcId;
    }

    public void setComcId(String comcId) {
        this.comcId = comcId;
    }

    public String getComcName() {
        return comcName;
    }

    public void setComcName(String comcName) {
        this.comcName = comcName;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }
}
