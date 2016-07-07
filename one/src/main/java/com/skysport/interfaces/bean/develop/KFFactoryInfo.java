package com.skysport.interfaces.bean.develop;

import com.skysport.core.bean.system.SelectItem;

import java.math.BigDecimal;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
public class KFFactoryInfo extends SelectItem {

    private String id;
    private String projectId;

    private String bomId;

    private String factoryId;

    private String factoryName;
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

    private String remark;
    private String updateTime;
    private int delFlag;

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


}
