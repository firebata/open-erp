package com.skysport.inerfaces.bean.develop;

import com.skysport.core.bean.system.SelectItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
public class KFPackaging extends SelectItem {

    private String id;

    private String projectId;

    private String bomId;

    private String bomName;

    private String packagingId;

    private String packagingsName;

    private String materialTypeId;

    private String classicId;

    private String spId;

    private String orderCount;

    private BigDecimal attritionRate;

    private BigDecimal unitPrice;

    private String totalAmount;

    private BigDecimal totalPrice;

    private String yearCode;

    private String productTypeId;

    private String serialNumber;

    private String specificationId;

    private String pantoneId;
    private List<KFMaterialPantone> pantoneIds;
    private List<KFMaterialPosition> positionIds;

    private String positionId;

    private String unitId;

    private String unitAmount;

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


    private String description;

    public String getBomName() {
        return bomName;
    }

    public void setBomName(String bomName) {
        this.bomName = bomName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId;
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

    public String getMaterialTypeId() {
        return materialTypeId;
    }

    public void setMaterialTypeId(String materialTypeId) {
        this.materialTypeId = materialTypeId;
    }

    public String getClassicId() {
        return classicId;
    }

    public void setClassicId(String classicId) {
        this.classicId = classicId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getAttritionRate() {
        return attritionRate;
    }

    public void setAttritionRate(BigDecimal attritionRate) {
        this.attritionRate = attritionRate;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getYearCode() {
        return yearCode;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public String getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(String productTypeId) {
        this.productTypeId = productTypeId;
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

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(String unitAmount) {
        this.unitAmount = unitAmount;
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
