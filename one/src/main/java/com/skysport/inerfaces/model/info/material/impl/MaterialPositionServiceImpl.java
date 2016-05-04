package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.info.MaterialPositionInfo;
import com.skysport.inerfaces.mapper.info.material.PositionInfoMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("materialPositionService")
public class MaterialPositionServiceImpl extends CommonServiceImpl<MaterialPositionInfo> implements InitializingBean {
    @Resource(name = "positionInfoMapper")
    private PositionInfoMapper positionInfoMapper;

    @Override
    public void afterPropertiesSet()  {
        commonMapper = positionInfoMapper;
    }
}
