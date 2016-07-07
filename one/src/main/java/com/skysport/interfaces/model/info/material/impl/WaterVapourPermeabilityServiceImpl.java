package com.skysport.interfaces.model.info.material.impl;

import com.skysport.interfaces.bean.info.WaterVapourPermeabilityInfo;
import com.skysport.interfaces.mapper.info.material.WaterVapourPermeabilityMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("waterVapourPermeabilityService")
public class WaterVapourPermeabilityServiceImpl extends CommonServiceImpl<WaterVapourPermeabilityInfo> implements InitializingBean {

    @Resource(name = "waterVapourPermeabilityMapper")
    private WaterVapourPermeabilityMapper waterVapourPermeabilityMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = waterVapourPermeabilityMapper;
    }
}
