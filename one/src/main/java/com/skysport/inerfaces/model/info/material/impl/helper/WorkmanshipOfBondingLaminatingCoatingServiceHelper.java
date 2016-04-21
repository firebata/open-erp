package com.skysport.inerfaces.model.info.material.impl.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.model.info.material.impl.WorkmanshipOfBondingLaminatingCoatingServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum WorkmanshipOfBondingLaminatingCoatingServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        WorkmanshipOfBondingLaminatingCoatingServiceImpl workmanshipOfBondingLaminatingCoatingService = SpringContextHolder.getBean("workmanshipOfBondingLaminatingCoatingService");
        List<SelectItem2> wblcItems = workmanshipOfBondingLaminatingCoatingService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("wblcItems", wblcItems);
    }

}
