package com.skysport.inerfaces.model.info.material.impl.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.info.material.impl.BondingLaminationCoatingServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum BondingLaminationCoatingServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        BondingLaminationCoatingServiceImpl bondingLaminationCoatingService = SpringContextHolder.getBean("bondingLaminationCoatingService");
        List<SelectItem2> blcItems = bondingLaminationCoatingService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushBom("blcItems", blcItems);
    }
}
