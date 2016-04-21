package com.skysport.inerfaces.bean.form.develop;

import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.inerfaces.bean.form.BaseQueyrForm;

/**
 * 说明:
 * Created by zhangjh on 2016/4/14.
 */
public class FactoryQuotePreQueryForm extends BaseQueyrForm {

    private FactoryQuoteInfo quoteInfo;

    public FactoryQuoteInfo getQuoteInfo() {
        return quoteInfo;
    }

    public void setQuoteInfo(FactoryQuoteInfo quoteInfo) {
        this.quoteInfo = quoteInfo;
    }
}
