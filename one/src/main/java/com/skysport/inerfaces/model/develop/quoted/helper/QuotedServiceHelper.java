package com.skysport.inerfaces.model.develop.quoted.helper;

import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.inerfaces.model.develop.accessories.helper.AccessoriesServiceHelper;
import com.skysport.inerfaces.model.develop.fabric.helper.FabricsServiceHelper;
import com.skysport.inerfaces.model.develop.packaging.helper.PackagingServiceHelper;
import com.skysport.inerfaces.model.info.sp.helper.SpInfoHelper;
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
     * 设置报价信息
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
        quotedInfo.setFactoryMargins(factory.getFactoryMargins());
        quotedInfo.setFactoryOffer(factory.getFactoryOffer());
        String spId = factory.getSpId();
        String spName = SpInfoHelper.SINGLETONE.turnSpIdToName(spId);
        quotedInfo.setSpId(factory.getSpId());
        quotedInfo.setSpName(spName);
        quotedInfo.setCosting(costing);
        quotedInfo.setBomId(bomId);
        return quotedInfo;
    }

    /**
     * @param bomInfo BomInfo
     * @return BigDecimal
     */
    private BigDecimal cacaulataCosting(BomInfo bomInfo) {
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
            costing.add(costingFabric);
        }
        if (null != costingAccessories) {
            costing.add(costingAccessories);
        }
        if (null != costingPackageing) {
            costing.add(costingPackageing);
        }
        return costing;
    }

}
