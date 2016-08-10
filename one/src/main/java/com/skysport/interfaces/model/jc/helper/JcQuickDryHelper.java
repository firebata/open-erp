package com.skysport.interfaces.model.jc.helper;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.jc.IJcQuickDryService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016-07-07.
 */
public enum JcQuickDryHelper {
    SINGLETONE;

    public void refreshSelect() {
        IJcQuickDryService service = SpringContextHolder.getBean("jcQuickDryServiceImpl");
        List<SelectItem2> items = service.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom(WebConstants.JcQuickDryItems, items);
    }
}
