package com.skysport.inerfaces.bean.develop;

/**
 * 说明:物料颜色
 * Created by zhangjh on 2016/1/19.
 */
public class KFMaterialPantone {
    private int id;
    private String pantoneId;
    private String pantoneName;
    private String materialId;
    private String remark;
    private String updateTime;
    private int delFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPantoneId() {
        return pantoneId;
    }

    public void setPantoneId(String pantoneId) {
        this.pantoneId = pantoneId;
    }

    public String getPantoneName() {
        return pantoneName;
    }

    public void setPantoneName(String pantoneName) {
        this.pantoneName = pantoneName;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
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
