package com.skysport.inerfaces.model.develop.quoted.helper;

import com.skysport.inerfaces.bean.develop.QuotedInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

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

    public QuotedInfo getInfo(HttpServletRequest request,int step) {
        QuotedInfo info = new QuotedInfo();
        info.setStep(step);
        return info;
    }
}
