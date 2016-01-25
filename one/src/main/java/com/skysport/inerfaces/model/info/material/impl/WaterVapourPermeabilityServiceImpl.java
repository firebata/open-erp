package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.inerfaces.bean.info.WaterVapourPermeabilityInfo;
import com.skysport.inerfaces.mapper.info.material.WaterVapourPermeabilityDao;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Component("waterVapourPermeabilityService")
public class WaterVapourPermeabilityServiceImpl extends CommonServiceImpl<WaterVapourPermeabilityInfo> implements InitializingBean {

    @Resource(name = "waterVapourPermeabilityDao")
    private WaterVapourPermeabilityDao waterVapourPermeabilityDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = waterVapourPermeabilityDao;
    }
}
