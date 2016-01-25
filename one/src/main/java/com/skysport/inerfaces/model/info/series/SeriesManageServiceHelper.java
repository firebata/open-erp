package com.skysport.inerfaces.model.info.series;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.info.series.impl.SeriesManageServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum SeriesManageServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        SeriesManageServiceImpl seriesManageService = SpringContextHolder.getBean("seriesManageService");
        List<SelectItem2> seriesItems = seriesManageService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushBom("seriesItems", seriesItems);
        SystemBaseInfo.SINGLETONE.pushProject("seriesItems", seriesItems);
    }
}
