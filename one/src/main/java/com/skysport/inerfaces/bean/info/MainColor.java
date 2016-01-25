package com.skysport.inerfaces.bean.info;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/15.
 */
public class MainColor {
    private String id;
    private String projectId;
    private String projectName;
    private String mainColorName;
    private String remark;
    private String updateTime;
    private int delFlag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getMainColorName() {
        return mainColorName;
    }

    public void setMainColorName(String mainColorName) {
        this.mainColorName = mainColorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
