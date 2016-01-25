package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.inerfaces.bean.info.SpecificationInfo;
import com.skysport.inerfaces.mapper.info.material.SpecificationDao;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Component("specificationService")
public class SpecificationServiceImpl extends CommonServiceImpl<SpecificationInfo> implements InitializingBean {


    @Resource(name = "specificationDao")
    private SpecificationDao specificationDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = specificationDao;
    }
}
