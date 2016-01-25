package com.skysport.inerfaces.model.info.factory;

import com.skysport.inerfaces.bean.info.FactoryInfo;
import com.skysport.inerfaces.mapper.info.FactoryManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("factoryManageService")
public class FactoryManageServiceImpl extends CommonServiceImpl<FactoryInfo> implements InitializingBean {
    @Resource(name = "factoryManageMapper")
    private FactoryManageMapper factoryManageDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = factoryManageDao;
    }
}
