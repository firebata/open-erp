package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.inerfaces.bean.info.MembraneThicknessInfo;
import com.skysport.inerfaces.mapper.info.material.MembraneThicknessDao;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Component("membraneThicknessService")
public class MembraneThicknessServiceImpl extends CommonServiceImpl<MembraneThicknessInfo> implements InitializingBean {
    @Resource(name = "membraneThicknessDao")
    private MembraneThicknessDao membraneThicknessDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = membraneThicknessDao;
    }
}
