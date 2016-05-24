package com.skysport.inerfaces.bean.develop;

import com.skysport.inerfaces.bean.relation.ProjectPojectItemBomSpVo;
import com.skysport.inerfaces.bean.task.ApproveVo;

import java.math.BigDecimal;

/**
 * 说明:
 * Created by zhangjh on 2015/9/9.
 */
public class QuotedInfo extends ProjectPojectItemBomSpVo implements ApproveVo {

    private String approveStatus;

    private String now;
    private Integer step;

    private String fabricId;

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
    private String stateCode;

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
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


    public String getMainFabricDescs() {
        return mainFabricDescs;
    }

    public void setMainFabricDescs(String mainFabricDescs) {
        this.mainFabricDescs = mainFabricDescs;
    }


    public String getMainFabricIds() {
        return mainFabricIds;
    }

    public void setMainFabricIds(String mainFabricIds) {
        this.mainFabricIds = mainFabricIds;
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

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public String getFabricId() {
        return fabricId;
    }

    public void setFabricId(String fabricId) {
        this.fabricId = fabricId;
    }


    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "QuotedInfo{" +
                "approveStatus='" + approveStatus + '\'' +
                ", mainFabricIds='" + mainFabricIds + '\'' +
                ", now='" + now + '\'' +
                ", step=" + step +
                ", mainFabricDescs='" + mainFabricDescs + '\'' +
                ", euroExchangeRates=" + euroExchangeRates +
                ", rates=" + rates +
                ", euroPrice=" + euroPrice +
                ", quotedPrice=" + quotedPrice +
                ", factoryOffer=" + factoryOffer +
                ", factoryMargins=" + factoryMargins +
                ", costing=" + costing +
                ", lpPrice=" + lpPrice +
                ", commission=" + commission +
                ", exchangeCosts=" + exchangeCosts +
                ", fabricId='" + fabricId + '\'' +
                '}';
    }

    @Override
    public String getStateCode() {
        return stateCode;
    }

    @Override
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }
}
