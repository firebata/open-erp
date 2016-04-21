package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.info.MaterialPositionInfo;
import com.skysport.inerfaces.mapper.info.material.PositionInfoMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("materialPositionService")
public class MaterialPositionServiceImpl extends CommonServiceImpl<MaterialPositionInfo> implements InitializingBean {
    @Resource(name = "positionManageDao")
    private PositionInfoMapper positionManageDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = positionManageDao;
    }
}
