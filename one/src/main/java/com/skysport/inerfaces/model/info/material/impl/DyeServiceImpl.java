package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.info.DyeInfo;
import com.skysport.inerfaces.mapper.info.material.DyeMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:染色方式
 * Created by zhangjh on 2015/6/25.
 */
@Service("dyeService")
public class DyeServiceImpl  extends CommonServiceImpl<DyeInfo> implements InitializingBean {
    @Resource(name = "dyeDao")
    private DyeMapper dyeDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = dyeDao;
    }
}
