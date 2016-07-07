package com.skysport.interfaces.bean.develop;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/26.
 */
public class ProjectCategoryInfo {

    private String id;
    private String projectId;
    private String projectName;
    //一级品类
    private String categoryAid;
    //二级品类
    private String categoryBid;

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

    public String getCategoryAid() {
        return categoryAid;
    }

    public void setCategoryAid(String categoryAid) {
        this.categoryAid = categoryAid;
    }

    public String getCategoryBid() {
        return categoryBid;
    }

    public void setCategoryBid(String categoryBid) {
        this.categoryBid = categoryBid;
    }
}
