package com.skysport.inerfaces.model.info.material.impl.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.model.info.material.impl.MaterialOfMembraneCoatingServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum MaterialOfMembraneCoatingServicHelper {
    SINGLETONE;

    public void refreshSelect() {
        MaterialOfMembraneCoatingServiceImpl materialOfMembraneCoatingService = SpringContextHolder.getBean("materialOfMembraneCoatingService");
        List<SelectItem2> momcItems = materialOfMembraneCoatingService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("momcItems", momcItems);
    }
}
