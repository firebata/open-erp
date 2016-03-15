package com.skysport.inerfaces.model.info.service.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.info.service.impl.PantoneManageServiceImpl;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/3/15.
 */
public enum PantoneManageServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        PantoneManageServiceImpl pantoneManageService = SpringContextHolder.getBean("pantoneManageService");
        List<SelectItem2> patonesItems = pantoneManageService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushProject("patonesItems", patonesItems);
    }

}
