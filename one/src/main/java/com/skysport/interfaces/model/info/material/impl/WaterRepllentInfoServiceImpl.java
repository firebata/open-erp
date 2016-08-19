package com.skysport.interfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.bean.info.WaterRepllentInfo;
import com.skysport.interfaces.mapper.info.material.WaterRepllentMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:防泼水
 * Created by zhangjh on 2015/6/25.
 */
@Service("waterRepllentInfoService")
public class WaterRepllentInfoServiceImpl extends CommonServiceImpl<WaterRepllentInfo> implements InitializingBean {
    @Resource(name = "waterRepllentMapper")
    private WaterRepllentMapper finishMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = finishMapper;
    }
}
