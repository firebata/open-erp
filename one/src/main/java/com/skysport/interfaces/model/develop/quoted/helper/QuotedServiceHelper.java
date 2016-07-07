package com.skysport.interfaces.model.develop.quoted.helper;

import com.skysport.interfaces.bean.develop.BomInfo;
import com.skysport.interfaces.bean.develop.FabricsInfo;
import com.skysport.interfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.interfaces.bean.develop.QuotedInfo;
import com.skysport.interfaces.model.develop.accessories.helper.AccessoriesServiceHelper;
import com.skysport.interfaces.model.develop.fabric.helper.FabricsServiceHelper;
import com.skysport.interfaces.model.develop.packaging.helper.PackagingServiceHelper;
import com.skysport.interfaces.model.info.sp.helper.SpInfoHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
public class QuotedServiceHelper {

    protected static transient Log logger = LogFactory.getLog(QuotedServiceHelper.class);

    private static QuotedServiceHelper quotedServiceHelper = new QuotedServiceHelper();

    private QuotedServiceHelper() {
        super();
    }

    public static QuotedServiceHelper getInstance() {
        return quotedServiceHelper;
    }

    /**
     * @param step int
     * @return QuotedInfo
     */
    public QuotedInfo getInfo(int step) {
        return getInfo(null, step);
    }

    /**
     * @param request HttpServletRequest
     * @param step    int
     * @return QuotedInfo
     */
    public QuotedInfo getInfo(HttpServletRequest request, int step) {
        QuotedInfo info = new QuotedInfo();
        info.setStep(step);
        return info;
    }

    /**
     * 计算报价信息
     *
     * @param bomInfo
     * @param fabricInfo
     * @return
     */
    public QuotedInfo getQuotedInfo(BomInfo bomInfo, FabricsInfo fabricInfo) {

        BigDecimal costing = cacaulataCosting(bomInfo);

        //报价参考的成衣厂信息
        FactoryQuoteInfo factory = FactoryQuoteServiceHelper.SINGLETONE.getFactoryRefer(bomInfo.getFactoryQuoteInfos());

        QuotedInfo quotedInfo = buildQuotedInfo(fabricInfo, factory, costing, bomInfo.getBomId());

        return quotedInfo;


    }

    /**
     * 设置报价信息：工厂报价和工厂利润率从参考的工厂报价中获取
     *
     * @param fabricInfo FabricsInfo
     * @param factory    FactoryQuoteInfo
     * @param costing    BigDecimal
     * @param bomId      String
     * @return QuotedInfo
     */
    private QuotedInfo buildQuotedInfo(FabricsInfo fabricInfo, FactoryQuoteInfo factory, BigDecimal costing, String bomId) {
        QuotedInfo quotedInfo = new QuotedInfo();
        quotedInfo.setFabricId(fabricInfo.getFabricId());
        quotedInfo.setMainFabricDescs(fabricInfo.getDescription());
        if (null != factory) {
            quotedInfo.setFactoryMargins(factory.getFactoryMargins());
            quotedInfo.setFactoryOffer(factory.getFactoryOffer());
            String spId = factory.getSpId();
            String spName = SpInfoHelper.SINGLETONE.turnSpIdToName(spId);
            quotedInfo.setSpId(spId);
            quotedInfo.setSpName(spName);
        }
        quotedInfo.setCosting(costing);
        quotedInfo.setBomId(bomId);
        return quotedInfo;
    }

    /**
     * @param bomInfo BomInfo
     * @return BigDecimal
     */
    public BigDecimal cacaulataCosting(BomInfo bomInfo) {
        //所有面料成本
        BigDecimal costingFabric = FabricsServiceHelper.SINGLETONE.caculateCostingFabric(bomInfo.getFabricItems());

        //所有辅料成本
        BigDecimal costingAccessories = AccessoriesServiceHelper.SINGLETONE.caculateCostingAccessories(bomInfo.getAccessoriesItems());
        //所有包材成本
        BigDecimal costingPackageing = PackagingServiceHelper.SINGLETONE.caculateCostingPackageing(bomInfo.getPackagingItems());
        return cacaulataCosting(costingFabric, costingAccessories, costingPackageing);
    }

    /**
     * 计算总成本
     *
     * @param costingFabric      面料总成本
     * @param costingAccessories 辅料总成本
     * @param costingPackageing  包材总成本
     * @return
     */
    private BigDecimal cacaulataCosting(BigDecimal costingFabric, BigDecimal costingAccessories, BigDecimal costingPackageing) {
        BigDecimal costing = new BigDecimal(0);
        if (null != costingFabric) {
            costing = costing.add(costingFabric);
        }
        if (null != costingAccessories) {
            costing = costing.add(costingAccessories);
        }
        if (null != costingPackageing) {
            costing = costing.add(costingPackageing);
        }
        return costing;
    }

}
