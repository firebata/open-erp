package com.skysport.inerfaces.model.info.material_classic;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.init.SpringContextHolder;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.model.info.material_classic.impl.MaterialClassicManageServiceImpl;

import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2015/7/7.
 */
public enum MaterialClassicManageServiceHelper {

    SINGLETONE;

    public void refreshSelect() {
        MaterialClassicManageServiceImpl materialClassicManageService = SpringContextHolder.getBean("materialClassicManageService");

        //面料
        List<SelectItem2> fabricClassicItems = materialClassicManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("fabricClassicItems", fabricClassicItems);

        //辅料材质
        List<SelectItem2> accessoriesClassicItems = materialClassicManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("accessoriesClassicItems", accessoriesClassicItems);

        //包材材质
        List<SelectItem2> pakingClassicItems = materialClassicManageService.querySelectList(null);
        SystemBaseInfoCachedMap.SINGLETONE.pushBom("pakingClassicItems", pakingClassicItems);


    }


}
