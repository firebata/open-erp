package com.skysport.inerfaces.model.info.material.impl.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.info.material.impl.MembraneThicknessServiceImpl;

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
        SystemBaseInfo.SINGLETONE.pushBom("mtItems", mtItems);
    }
}
