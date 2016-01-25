package com.skysport.inerfaces.bean.develop;

import com.skysport.core.bean.CommonVo;

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
    private Float orderCount;
    private Float attritionRate;
    private Float unitPrice;
    private Float totalAmount;
    private Float totalPrice;

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

    public Float getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Float orderCount) {
        this.orderCount = orderCount;
    }

    public Float getAttritionRate() {
        return attritionRate;
    }

    public void setAttritionRate(Float attritionRate) {
        this.attritionRate = attritionRate;
    }

    public Float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
