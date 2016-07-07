package com.skysport.interfaces.bean.develop;

import java.math.BigDecimal;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
public class PackagingInfo extends MaterialInfo {

    private String id;


    private String packagingId;

    private String packagingsName;


    private String serialNumber;

    private String specificationId;

    private String pantoneId;
    private List<KFMaterialPantone> pantoneIds;
    private List<KFMaterialPosition> positionIds;


    private int delFlag;

    private String remark;

    private String updateTime;

    /**
     * 序号
     */
    private String nameNum;

    /**
     * 工艺要求
     */
    private String techRequired;

    /**
     * 长
     */
    private BigDecimal length;

    /**
     * 宽
     */
    private BigDecimal width;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(String packagingId) {
        this.packagingId = packagingId;
    }

    public String getPackagingsName() {
        return packagingsName;
    }

    public void setPackagingsName(String packagingsName) {
        this.packagingsName = packagingsName;
    }


    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

    public String getPantoneId() {
        return pantoneId;
    }

    public void setPantoneId(String pantoneId) {
        this.pantoneId = pantoneId;
    }


    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
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

    public String getNameNum() {
        return nameNum;
    }

    public void setNameNum(String nameNum) {
        this.nameNum = nameNum;
    }

    public String getTechRequired() {
        return techRequired;
    }

    public void setTechRequired(String techRequired) {
        this.techRequired = techRequired;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public List<KFMaterialPosition> getPositionIds() {
        return positionIds;
    }

    public void setPositionIds(List<KFMaterialPosition> positionIds) {
        this.positionIds = positionIds;
    }

    public List<KFMaterialPantone> getPantoneIds() {
        return pantoneIds;
    }

    public void setPantoneIds(List<KFMaterialPantone> pantoneIds) {
        this.pantoneIds = pantoneIds;
    }
}
