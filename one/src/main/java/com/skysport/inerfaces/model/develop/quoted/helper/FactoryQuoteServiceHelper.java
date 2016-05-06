package com.skysport.inerfaces.model.develop.quoted.helper;

import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.inerfaces.constant.WebConstants;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016-05-06.
 */
public enum FactoryQuoteServiceHelper {
    SINGLETONE;


    public FactoryQuoteInfo getFactoryRefer(List<FactoryQuoteInfo> factoryQuoteInfos) {
        FactoryQuoteInfo info = null;
        for (FactoryQuoteInfo factoryQuoteInfo : factoryQuoteInfos) {
            int quoteReference = factoryQuoteInfo.getQuoteReference();
            if (WebConstants.QUOTE_REFERENCE_YES == quoteReference) {
                info = factoryQuoteInfo;
                break;
            }
        }
        return info;
    }
}
