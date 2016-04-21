package com.skysport.inerfaces.model.info.customer.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.model.info.customer.service.impl.CustomerManageServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum CustomerManageServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        CustomerManageServiceImpl customerManageService = SpringContextHolder. getBean("customerManageService");
        List<SelectItem2> customerItems = customerManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("customerItems", customerItems);
        SystemBaseInfoCachedMap.SINGLETONE.pushProject("customerItems", customerItems);
    }
}
