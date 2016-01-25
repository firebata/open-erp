package com.skysport.inerfaces.model.info.sex;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.info.sex.impl.SexManageServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum SexManageServiceHelper {
    SINGLETONE;

    public void refreshSelect() {
        SexManageServiceImpl sexManageService = SpringContextHolder.getBean("sexManageService");
        List<SelectItem2> sexItems = sexManageService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushProject("sexItems", sexItems);
    }
}
