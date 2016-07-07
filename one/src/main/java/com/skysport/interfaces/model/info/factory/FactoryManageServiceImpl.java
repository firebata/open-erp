package com.skysport.interfaces.model.info.factory;

import com.skysport.interfaces.bean.info.FactoryInfo;
import com.skysport.interfaces.mapper.info.FactoryMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("factoryManageService")
public class FactoryManageServiceImpl extends CommonServiceImpl<FactoryInfo> implements InitializingBean {
    @Resource(name = "factoryMapper")
    private FactoryMapper factoryManageDao;

    @Override
    public void afterPropertiesSet() {
        commonMapper = factoryManageDao;
    }
}
