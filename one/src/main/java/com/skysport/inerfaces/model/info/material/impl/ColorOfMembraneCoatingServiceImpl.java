package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.info.ColorOfMembraneCoatingInfo;
import com.skysport.inerfaces.mapper.info.material.ColorOfMembraneCoatingMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("colorOfMembraneCoatingService")
public class ColorOfMembraneCoatingServiceImpl extends CommonServiceImpl<ColorOfMembraneCoatingInfo> implements InitializingBean {
    @Resource(name = "colorOfMembraneCoatingMapper")
    private ColorOfMembraneCoatingMapper colorOfMembraneCoatingMapper;

    @Override
    public void afterPropertiesSet()  {
        commonMapper = colorOfMembraneCoatingMapper;
    }

}
