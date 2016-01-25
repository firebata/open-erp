package com.skysport.inerfaces.model.develop.fabric.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.instance.SystemBaseInfo;
import com.skysport.inerfaces.model.develop.fabric.impl.FabricsServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum FabricsServiceHelper {

    SINGLETONE;

    public void refreshSelect() {
        FabricsServiceImpl fabricsManageService = SpringContextHolder.getBean("fabricsManageService");
        List<SelectItem2> fabricsItems = fabricsManageService.querySelectList(null);
        SystemBaseInfo.SINGLETONE.pushBom("fabricsItems", fabricsItems);
    }





}
