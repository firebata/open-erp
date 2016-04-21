package com.skysport.inerfaces.model.info.category;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.model.info.category.impl.CategoryManageServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum CategoryManageServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        CategoryManageServiceImpl categoryManageService = SpringContextHolder.getBean("categoryManageService");
        List<SelectItem2> categoryAItems = categoryManageService.querySelectListByLevelId(WebConstants.CATEGORY_A_LEVEL);
        List<SelectItem2> categoryBItems = categoryManageService.querySelectListByLevelId(WebConstants.CATEGORY_B_LEVEL);
        SystemBaseInfoCachedMap.SINGLETONE.pushProject("categoryAItems", categoryAItems);
        SystemBaseInfoCachedMap.SINGLETONE.pushProject("categoryBItems", categoryBItems);
    }
}
