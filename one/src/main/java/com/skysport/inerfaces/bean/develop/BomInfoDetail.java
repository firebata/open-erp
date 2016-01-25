package com.skysport.inerfaces.bean.develop;

import java.math.BigDecimal;
import java.util.List;

/**
 * 说明:BomInfoDetail-bom详细信息
 * Created by zhangjh on 2015/10/21.
 */
public class BomInfoDetail {

    /**
     *
     */
    private String bomId;
    /***
     *
     */
    private String bomName;


    private List<FabricsInfo> fabricsInfos;

    private List<AccessoriesInfo> accessoriesInfos;

    private List<KFPackaging> packagings;
    /**
     *
     */
    private String item;
    /***
     *
     */
    private String description;
    /***
     *
     */
    private String color;
    /***
     *
     */
    private BigDecimal unitId;
    /***
     *
     */
    private int orderCount;
    /***
     *
     */
    private BigDecimal unitAmount;
    /***
     *
     */
    private BigDecimal totalAmount;
    /***
     *
     */
    private String remark;

    public List<FabricsInfo> getFabricsInfos() {
        return fabricsInfos;
    }

    public void setFabricsInfos(List<FabricsInfo> fabricsInfos) {
        this.fabricsInfos = fabricsInfos;
    }

    public List<AccessoriesInfo> getAccessoriesInfos() {
        return accessoriesInfos;
    }

    public void setAccessoriesInfos(List<AccessoriesInfo> accessoriesInfos) {
        this.accessoriesInfos = accessoriesInfos;
    }

    public List<KFPackaging> getPackagings() {
        return packagings;
    }

    public void setPackagings(List<KFPackaging> packagings) {
        this.packagings = packagings;
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getUnitId() {
        return unitId;
    }

    public void setUnitId(BigDecimal unitId) {
        this.unitId = unitId;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
