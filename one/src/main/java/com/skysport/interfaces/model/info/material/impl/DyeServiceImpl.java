package com.skysport.interfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.bean.info.DyeInfo;
import com.skysport.interfaces.mapper.info.material.DyeMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:染色方式
 * Created by zhangjh on 2015/6/25.
 */
@Service("dyeService")
public class DyeServiceImpl extends CommonServiceImpl<DyeInfo> implements InitializingBean {
    @Resource(name = "dyeMapper")
    private DyeMapper dyeMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = dyeMapper;
    }
}
