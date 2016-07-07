package com.skysport.interfaces.model.info.year_conf;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.interfaces.model.info.year_conf.impl.YearConfManageServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum YearConfManageServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        YearConfManageServiceImpl yearConfManageService = SpringContextHolder.getBean("yearConfManageService");
        List<SelectItem2> yearItems = yearConfManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("yearItems", yearItems);
        SystemBaseInfoCachedMap.SINGLETONE.pushProject("yearItems", yearItems);
    }
}
