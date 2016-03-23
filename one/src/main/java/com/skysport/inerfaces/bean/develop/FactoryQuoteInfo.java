package com.skysport.inerfaces.bean.develop;

import java.math.BigDecimal;

/**
 * 说明:
 * Created by zhangjh on 2015/10/8.
 */
public class FactoryQuoteInfo {

    /**
     * id
     */
    private int id;

    /**
     * 工厂报价id
     */
    private String factoryQuoteId;

    /**
     * 面料交货时间
     */
    private String fabricsEndDate;

    /**
     * 辅料交货时间
     */
    private String accessoriesEndDate;

    /**
     * 成衣报价时间
     */
    private String preOfferDate;

    /**
     * 成衣收到时间
     */
    private String clothReceivedDate;

    /**
     * 订单数量
     */
    private int offerAmount;

    /**
     * 是否参考报价
     */
    private int quoteReference;

    /**
     * 工厂id
     */
    private String spId;

    /**
     * 工厂欧元报价(€)
     */
    private BigDecimal euroPrice;

    /**
     * 工厂报价
     */
    private BigDecimal factoryOffer;

    /**
     * 工厂报价利润率
     */
    private BigDecimal factoryMargins;

    /**
     * 序号
     */
    private String nameNum;

    private String remark;

    private String updateTime;

    private int delFlag;

    /**
     * 成本核算
     */
    private BigDecimal costing;

    private String bomId;

    private KfProductionInstructionEntity productionInstruction;

    public BigDecimal getCosting() {
        return costing;
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

    public void setCosting(BigDecimal costing) {
        this.costing = costing;
    }

    public String getBomId() {
        return bomId;
    }

    public void setBomId(String bomId) {
        this.bomId = bomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFactoryQuoteId() {
        return factoryQuoteId;
    }

    public void setFactoryQuoteId(String factoryQuoteId) {
        this.factoryQuoteId = factoryQuoteId;
    }

    public String getFabricsEndDate() {
        return fabricsEndDate;
    }

    public void setFabricsEndDate(String fabricsEndDate) {
        this.fabricsEndDate = fabricsEndDate;
    }

    public String getAccessoriesEndDate() {
        return accessoriesEndDate;
    }

    public void setAccessoriesEndDate(String accessoriesEndDate) {
        this.accessoriesEndDate = accessoriesEndDate;
    }

    public String getPreOfferDate() {
        return preOfferDate;
    }

    public void setPreOfferDate(String preOfferDate) {
        this.preOfferDate = preOfferDate;
    }

    public String getClothReceivedDate() {
        return clothReceivedDate;
    }

    public void setClothReceivedDate(String clothReceivedDate) {
        this.clothReceivedDate = clothReceivedDate;
    }

    public int getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(int offerAmount) {
        this.offerAmount = offerAmount;
    }

    public int getQuoteReference() {
        return quoteReference;
    }

    public void setQuoteReference(int quoteReference) {
        this.quoteReference = quoteReference;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public BigDecimal getEuroPrice() {
        return euroPrice;
    }

    public void setEuroPrice(BigDecimal euroPrice) {
        this.euroPrice = euroPrice;
    }

    public BigDecimal getFactoryOffer() {
        return factoryOffer;
    }

    public void setFactoryOffer(BigDecimal factoryOffer) {
        this.factoryOffer = factoryOffer;
    }

    public BigDecimal getFactoryMargins() {
        return factoryMargins;
    }

    public void setFactoryMargins(BigDecimal factoryMargins) {
        this.factoryMargins = factoryMargins;
    }

    public String getNameNum() {
        return nameNum;
    }

    public void setNameNum(String nameNum) {
        this.nameNum = nameNum;
    }

    public KfProductionInstructionEntity getProductionInstruction() {
        return productionInstruction;
    }

    public void setProductionInstruction(KfProductionInstructionEntity productionInstruction) {
        this.productionInstruction = productionInstruction;
    }
}
