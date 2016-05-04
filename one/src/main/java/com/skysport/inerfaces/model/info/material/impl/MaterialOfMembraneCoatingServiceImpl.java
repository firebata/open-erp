package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.info.MaterialOfMembraneCoatingInfo;
import com.skysport.inerfaces.mapper.info.material.MaterialOfMembraneCoatingMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("materialOfMembraneCoatingService")
public class MaterialOfMembraneCoatingServiceImpl  extends CommonServiceImpl<MaterialOfMembraneCoatingInfo> implements InitializingBean {
    @Resource(name = "materialOfMembraneCoatingMapper")
    private MaterialOfMembraneCoatingMapper materialOfMembraneCoatingMapper;

    @Override
    public void afterPropertiesSet()  {
        commonMapper = materialOfMembraneCoatingMapper;
    }
}
