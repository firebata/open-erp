package com.skysport.inerfaces.model.info.material.impl.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.info.material.impl.MaterialPositionServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum PositionServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        MaterialPositionServiceImpl materialPositionService = SpringContextHolder.getBean("materialPositionService");
        List<SelectItem2> positionItems = materialPositionService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushBom("positionItems", positionItems);
    }
}
