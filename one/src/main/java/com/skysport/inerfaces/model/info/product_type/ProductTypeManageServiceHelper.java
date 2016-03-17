package com.skysport.inerfaces.model.info.product_type;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.model.info.product_type.impl.ProductTypeManageServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum ProductTypeManageServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        ProductTypeManageServiceImpl productTypeManageService = SpringContextHolder.getBean("productTypeManageService");
        List<SelectItem2> productTypeItems = productTypeManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("productTypeItems", productTypeItems);
    }
}
