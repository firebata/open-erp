package com.skysport.interfaces.model.info.material.impl.helper;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.info.material.impl.MembraneThicknessServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum MembraneThicknessServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        MembraneThicknessServiceImpl membraneThicknessService = SpringContextHolder.getBean("membraneThicknessService");
        List<SelectItem2> mtItems = membraneThicknessService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom(WebConstants.MTITEMS, mtItems);
    }
}
