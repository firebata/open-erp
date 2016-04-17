package com.skysport.inerfaces.bean.develop;

import com.skysport.inerfaces.bean.common.UploadFileInfo;

import java.util.List;
import java.util.Map;

/**
 * 类说明:项目的BOM信息
 * Created by zhangjh on 2015/7/2.
 */
public class ProjectBomInfo extends ProjectBaseInfo {
    //
    private String id;
    private String projectId;
    private String parentProjectId;
    private String projectName;
    private String categoryAid;
    private String categoryAname;
    private String categoryBid;
    private String categoryBname;
    private int collectionNumber;
    private String mainColorNames;

    private List<SexColor> sexColors;
    private String sexIds;
    private String remark;
    private String updateTime;
    private int delFlag;
    private String approveStatus;

    private List<UploadFileInfo> fileInfos;


    public void buildBomInfo(ProjectBomInfo info) {
        String projectId = info.getNatrualkey();
        String customerId = info.getCustomerId();
        String areaId = info.getAreaId();
        String seriesId = info.getSeriesId();
        String categoryAid = info.getCategoryAid();
        String categoryBid = info.getCategoryBid();
        this.setProjectId(projectId);
        this.setCustomerId(customerId);
        this.setAreaId(areaId);
        this.setSeriesId(seriesId);
        this.setCategoryAid(categoryAid);
        this.setCategoryBid(categoryBid);
    }

    /**
     * 存放初始化文件上传控件中的信息
     */
    private Map<String, Object> fileinfosMap;

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

    public String getCategoryAname() {
        return categoryAname;
    }

    public void setCategoryAname(String categoryAname) {
        this.categoryAname = categoryAname;
    }

    public String getCategoryBid() {
        return categoryBid;
    }

    public void setCategoryBid(String categoryBid) {
        this.categoryBid = categoryBid;
    }

    public String getCategoryBname() {
        return categoryBname;
    }

    public void setCategoryBname(String categoryBname) {
        this.categoryBname = categoryBname;
    }

    public int getCollectionNumber() {
        return collectionNumber;
    }

    public void setCollectionNumber(int collectionNumber) {
        this.collectionNumber = collectionNumber;
    }

    public String getMainColorNames() {
        return mainColorNames;
    }

    public void setMainColorNames(String mainColorNames) {
        this.mainColorNames = mainColorNames;
    }

    public String getSexIds() {
        return sexIds;
    }

    public void setSexIds(String sexIds) {
        this.sexIds = sexIds;
    }

    public String getParentProjectId() {
        return parentProjectId;
    }

    public void setParentProjectId(String parentProjectId) {
        this.parentProjectId = parentProjectId;
    }

    public List<SexColor> getSexColors() {
        return sexColors;
    }

    public void setSexColors(List<SexColor> sexColors) {
        this.sexColors = sexColors;
    }

    @Override
    public ProjectBomInfo clone() throws CloneNotSupportedException {
        ProjectBomInfo projectBomInfo = (ProjectBomInfo) super.clone();
        return projectBomInfo;
    }

    public List<UploadFileInfo> getFileInfos() {
        return fileInfos;
    }

    public void setFileInfos(List<UploadFileInfo> fileInfos) {
        this.fileInfos = fileInfos;
    }

    public Map<String, Object> getFileinfosMap() {
        return fileinfosMap;
    }

    public void setFileinfosMap(Map<String, Object> fileinfosMap) {
        this.fileinfosMap = fileinfosMap;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    @Override
    public String toString() {
        return "ProjectBomInfo{" +
                "id='" + id + '\'' +
                ", projectId='" + projectId + '\'' +
                ", parentProjectId='" + parentProjectId + '\'' +
                ", projectName='" + projectName + '\'' +
                ", categoryAid='" + categoryAid + '\'' +
                ", categoryAname='" + categoryAname + '\'' +
                ", categoryBid='" + categoryBid + '\'' +
                ", categoryBname='" + categoryBname + '\'' +
                ", collectionNumber=" + collectionNumber +
                ", mainColorNames='" + mainColorNames + '\'' +
                ", sexColors=" + sexColors +
                ", sexIds='" + sexIds + '\'' +
                ", remark='" + remark + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", delFlag=" + delFlag +
                ", approveStatus='" + approveStatus + '\'' +
                ", fileInfos=" + fileInfos +
                ", fileinfosMap=" + fileinfosMap +
                '}';
    }
}
