package com.skysport.interfaces.bean.develop;

import com.skysport.core.bean.system.SelectItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * 物料信息
 * Created by zhangjh on 2015/6/23.
 */
public class MaterialInfo extends SelectItem {

    private String id;
    private String customerName;
    /**
     * 系列名称
     */
    private String seriesName;
    private String projectId;
    private String bomId;
    private String bomName;
    private String materialTypeId;
    private String classicId;
    private String spId;
    private String orderCount;
    private String attritionRate;
    private String unitPrice;
    private BigDecimal colorPrice;
    private BigDecimal colorAmount;
    private String totalAmount;
    private String totalPrice;
    private String yearCode;
    private String productTypeId;
    private String pantoneId;
    private String positionId;

    private String unitId;
    private String unitAmount;
    private List<KFMaterialPantone> pantoneIds;
    private List<KFMaterialPosition> positionIds;
    private String description;
    private int delFlag;
    private String remark;
    private String updateTime;

    public BigDecimal getColorPrice() {
        return colorPrice;
    }

    public void setColorPrice(BigDecimal colorPrice) {
        this.colorPrice = colorPrice;
    }

    public BigDecimal getColorAmount() {
        return colorAmount;
    }

    public void setColorAmount(BigDecimal colorAmount) {
        this.colorAmount = colorAmount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
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

    public String getBomName() {
        return bomName;
    }

    public void setBomName(String bomName) {
        this.bomName = bomName;
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

    public String getAttritionRate() {
        return attritionRate;
    }

    public void setAttritionRate(String attritionRate) {
        this.attritionRate = attritionRate;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
