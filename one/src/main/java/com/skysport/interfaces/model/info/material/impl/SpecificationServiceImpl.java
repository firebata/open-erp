package com.skysport.interfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.bean.info.SpecificationInfo;
import com.skysport.interfaces.mapper.info.material.SpecificationMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("specificationService")
public class SpecificationServiceImpl extends CommonServiceImpl<SpecificationInfo> implements InitializingBean {


    @Resource(name = "specificationMapper")
    private SpecificationMapper specificationMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = specificationMapper;
    }
}
