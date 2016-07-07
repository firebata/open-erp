package com.skysport.interfaces.model.info.area.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.interfaces.model.info.area.impl.AreaServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum AreaManageServiceHelper {

    SINGLETONE;

    /**
     *
     */
    public void refreshSelect() {
        AreaServiceImpl areaManageService = SpringContextHolder.getBean("areaManageService");
        List<SelectItem2> areaItems = areaManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("areaItems", areaItems);
        SystemBaseInfoCachedMap.SINGLETONE.pushProject("areaItems", areaItems);
    }
}
