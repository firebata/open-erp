package com.skysport.inerfaces.model.info.material.impl.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.model.info.material.impl.DyeServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum DyeServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        DyeServiceImpl dyeService = SpringContextHolder.getBean("dyeService");
        List<SelectItem2> dyeItems = dyeService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("dyeItems", dyeItems);
    }
}
