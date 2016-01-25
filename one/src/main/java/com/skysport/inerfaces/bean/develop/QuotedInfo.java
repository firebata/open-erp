package com.skysport.inerfaces.bean.develop;

import java.math.BigDecimal;

/**
 * 说明:
 * Created by zhangjh on 2015/9/9.
 */
public class QuotedInfo {

    private int id;

    private String projectId;

    private String projectItemId;
    /**
     *
     */
    private String bomId;

    private String spId;

    private String mainFabricIds;

    /**
     *
     */
    private String mainFabricDescs;

    /**
     * 欧元汇率
     */
    private BigDecimal euroExchangeRates;
    /**
     * 利润率
     */
    private BigDecimal rates;
    /**
     * 工厂欧元报价(€)
     */
    private BigDecimal euroPrice;
    /**
     * 给客户报价
     */
    private BigDecimal quotedPrice;

    private String remark;

    private String updateTime;

    private int delFlag;

    /**
     * 工厂报价
     */
    private BigDecimal factoryOffer;
    /**
     * 工厂报价利润率
     */
    private BigDecimal factoryMargins;

    /**
     * 成本核算
     */
    private BigDecimal costing;
    /**
     * 包装费
     */
    private BigDecimal lpPrice;
    /**
     * 佣金
     */
    private BigDecimal commission;

    /**
     * 换汇成本
     */
    private BigDecimal exchangeCosts;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public BigDecimal getLpPrice() {
        return lpPrice;
    }

    public void setLpPrice(BigDecimal lpPrice) {
        this.lpPrice = lpPrice;
    }

    public BigDecimal getCommission() {
        return commission;
    }

    public void setCommission(BigDecimal commission) {
        this.commission = commission;
    }


    public BigDecimal getExchangeCosts() {
        return exchangeCosts;
    }

    public void setExchangeCosts(BigDecimal exchangeCosts) {
        this.exchangeCosts = exchangeCosts;
    }

    public BigDecimal getCosting() {
        return costing;
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

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }


    public String getMainFabricDescs() {
        return mainFabricDescs;
    }

    public void setMainFabricDescs(String mainFabricDescs) {
        this.mainFabricDescs = mainFabricDescs;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectItemId() {
        return projectItemId;
    }

    public void setProjectItemId(String projectItemId) {
        this.projectItemId = projectItemId;
    }

    public String getMainFabricIds() {
        return mainFabricIds;
    }

    public void setMainFabricIds(String mainFabricIds) {
        this.mainFabricIds = mainFabricIds;
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

    public BigDecimal getEuroExchangeRates() {
        return euroExchangeRates;
    }

    public void setEuroExchangeRates(BigDecimal euroExchangeRates) {
        this.euroExchangeRates = euroExchangeRates;
    }

    public BigDecimal getRates() {
        return rates;
    }

    public void setRates(BigDecimal rates) {
        this.rates = rates;
    }

    public BigDecimal getEuroPrice() {
        return euroPrice;
    }

    public void setEuroPrice(BigDecimal euroPrice) {
        this.euroPrice = euroPrice;
    }

    public BigDecimal getQuotedPrice() {
        return quotedPrice;
    }

    public void setQuotedPrice(BigDecimal quotedPrice) {
        this.quotedPrice = quotedPrice;
    }

}
