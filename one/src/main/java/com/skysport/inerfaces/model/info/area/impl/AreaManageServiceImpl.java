package com.skysport.inerfaces.model.info.area.impl;

import com.skysport.inerfaces.bean.info.AreaInfo;
import com.skysport.inerfaces.mapper.info.AreaManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("areaManageService")
public class AreaManageServiceImpl extends CommonServiceImpl<AreaInfo> implements InitializingBean {
    @Resource(name = "areaManageDao")
    private AreaManageMapper areaManageMapper;

    @Override
    public void afterPropertiesSet()  {
        commonDao = areaManageMapper;
    }
}
