package com.skysport.interfaces.bean.develop;

import com.skysport.core.bean.CommonVo;

import java.math.BigDecimal;

/**
 * 类说明:物料供应商信息
 * Created by zhangjh on 2015/7/2.
 */
public class MaterialSpInfo implements CommonVo {
    private String id;
    private String remark;
    private String updateTime;
    private int delFlag;
    private String materialId;
    private String spId;
    private BigDecimal orderCount;

    private BigDecimal attritionRate;
    private BigDecimal unitPrice;
    private BigDecimal colorPrice;
    private BigDecimal colorAmount;
    private BigDecimal totalAmount;

    private BigDecimal totalPrice;


    public MaterialSpInfo() {
        super();
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

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public BigDecimal getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(BigDecimal orderCount) {
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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getColorAmount() {
        return colorAmount;
    }

    public void setColorAmount(BigDecimal colorAmount) {
        this.colorAmount = colorAmount;
    }

    public BigDecimal getColorPrice() {
        return colorPrice;
    }

    public void setColorPrice(BigDecimal colorPrice) {
        this.colorPrice = colorPrice;
    }
}
