package com.skysport.interfaces.bean.relation;

/**
 * 说明:
 * Created by zhangjh on 2016/4/15.
 */
public class ProjectItemProjectIdVo {
    private String projectId;
    private String parentProjectId;

    public ProjectItemProjectIdVo() {
        super();
    }

    public ProjectItemProjectIdVo(String projectId, String parentProjectId) {
        this.projectId = projectId;
        this.parentProjectId = parentProjectId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getParentProjectId() {
        return parentProjectId;
    }

    public void setParentProjectId(String parentProjectId) {
        this.parentProjectId = parentProjectId;
    }
}
