package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.inerfaces.bean.info.MaterialOfMembraneCoatingInfo;
import com.skysport.inerfaces.mapper.info.material.MaterialOfMembraneCoatingDao;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Component("materialOfMembraneCoatingService")
public class MaterialOfMembraneCoatingServiceImpl  extends CommonServiceImpl<MaterialOfMembraneCoatingInfo> implements InitializingBean {
    @Resource(name = "materialOfMembraneCoatingDao")
    private MaterialOfMembraneCoatingDao materialOfMembraneCoatingDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = materialOfMembraneCoatingDao;
    }
}
