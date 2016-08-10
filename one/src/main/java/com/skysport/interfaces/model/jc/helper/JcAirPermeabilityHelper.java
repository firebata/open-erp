package com.skysport.interfaces.model.jc.helper;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.jc.IJcAirPermeabilityService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016-07-07.
 */
public enum JcAirPermeabilityHelper {
    SINGLETONE;

    public void refreshSelect() {
        IJcAirPermeabilityService service = SpringContextHolder.getBean("jcAirPermeabilityServiceImpl");
        List<SelectItem2> dyeItems = service.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom(WebConstants.AirPermeabilityItems, dyeItems);
    }
}
