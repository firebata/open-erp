package com.skysport.interfaces.model.relation.material_sp.helper;

import com.skysport.interfaces.bean.develop.MaterialSpInfo;

import java.math.BigDecimal;

/**
 * 说明:
 * Created by zhangjh on 2016-05-06.
 */
public enum MaterialSpServiceHelper {
    SINGLETONE;

    public BigDecimal caculateCosting(BigDecimal bigDecimal, MaterialSpInfo materialSpInfo) {
        BigDecimal colorPrice = materialSpInfo.getColorPrice();
        if (null != colorPrice) {
            return colorPrice;
        }
        return new BigDecimal(0);
    }
}
