package com.skysport.interfaces.model.jc.helper;

import com.skysport.core.bean.SpringContextHolder;
import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.model.jc.IJcSeamWaterpressureService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016-07-07.
 */
public enum JcSeamWaterpressureHelper {
    SINGLETONE;

    public void refreshSelect() {
        IJcSeamWaterpressureService service = SpringContextHolder.getBean("jcSeamWaterpressureServiceImpl");
        List<SelectItem2> items = service.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom(WebConstants.JcSeamWaterpressureItems, items);
    }
}
