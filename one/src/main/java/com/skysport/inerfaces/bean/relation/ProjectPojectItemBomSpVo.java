package com.skysport.inerfaces.bean.relation;

import com.skysport.core.bean.system.SelectItem;

/**
 * 说明:
 * Created by zhangjh on 2016/4/29.
 */
public class ProjectPojectItemBomSpVo extends SelectItem {
    private String id;
    private String projectId;
    private String projectName;
    private String projectItemId;
    private String projectItemName;
    /**
     *
     */
    private String bomId;
    private String bomName;

    private String spId;
    private String spName;

    private String remark;

    private String updateTime;

    private int delFlag;

    @Override
    public String getId() {
        return id;
    }

    @Override
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

    public String getProjectItemId() {
        return projectItemId;
    }

    public void setProjectItemId(String projectItemId) {
        this.projectItemId = projectItemId;
    }

    public String getProjectItemName() {
        return projectItemName;
    }

    public void setProjectItemName(String projectItemName) {
        this.projectItemName = projectItemName;
    }

    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId;
    }

    public String getBomName() {
        return bomName;
    }

    public void setBomName(String bomName) {
        this.bomName = bomName;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
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

    @Override
    public int getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }
}
