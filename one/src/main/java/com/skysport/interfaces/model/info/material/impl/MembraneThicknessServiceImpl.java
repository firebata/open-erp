package com.skysport.interfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.bean.info.MembraneThicknessInfo;
import com.skysport.interfaces.mapper.info.material.MembraneThicknessMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("membraneThicknessService")
public class MembraneThicknessServiceImpl extends CommonServiceImpl<MembraneThicknessInfo> implements InitializingBean {
    @Resource(name = "membraneThicknessMapper")
    private MembraneThicknessMapper membraneThicknessMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = membraneThicknessMapper;
    }
}
