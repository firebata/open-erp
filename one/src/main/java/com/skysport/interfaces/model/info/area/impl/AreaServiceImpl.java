package com.skysport.interfaces.model.info.area.impl;

import com.skysport.interfaces.bean.info.AreaInfo;
import com.skysport.interfaces.mapper.info.AreaMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("areaManageService")
public class AreaServiceImpl extends CommonServiceImpl<AreaInfo> implements InitializingBean {
    @Resource(name = "areaMapper")
    private AreaMapper areaMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = areaMapper;
    }
}
