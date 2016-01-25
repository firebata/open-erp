package com.skysport.inerfaces.model.info.material.impl.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.info.material.impl.ColorOfMembraneCoatingServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum ColorOfMembraneCoatingServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        ColorOfMembraneCoatingServiceImpl colorOfMembraneCoatingService = SpringContextHolder.getBean("colorOfMembraneCoatingService");
        List<SelectItem2> comocItems = colorOfMembraneCoatingService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushBom("comocItems", comocItems);
    }
}
