package com.skysport.inerfaces.bean.develop;

/**
 * 说明:子项目性别属性和颜色
 * Created by zhangjh on 2015/11/4.
 */
public class SexColor {
    private String id;
    private String projectId;
    private String sexIdChild;
    private String sexName;
    private String mainColorNames;

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

    public String getSexIdChild() {
        return sexIdChild;
    }

    public void setSexIdChild(String sexIdChild) {
        this.sexIdChild = sexIdChild;
    }

    public String getSexName() {
        return sexName;
    }

    public void setSexName(String sexName) {
        this.sexName = sexName;
    }

    public String getMainColorNames() {
        return mainColorNames;
    }

    public void setMainColorNames(String mainColorNames) {
        this.mainColorNames = mainColorNames;
    }
}
