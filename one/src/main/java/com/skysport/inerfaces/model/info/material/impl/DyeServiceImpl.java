package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.inerfaces.bean.info.DyeInfo;
import com.skysport.inerfaces.mapper.info.material.DyeManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类说明:染色方式
 * Created by zhangjh on 2015/6/25.
 */
@Component("dyeService")
public class DyeServiceImpl  extends CommonServiceImpl<DyeInfo> implements InitializingBean {
    @Resource(name = "dyeDao")
    private DyeManageMapper dyeDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = dyeDao;
    }
}
