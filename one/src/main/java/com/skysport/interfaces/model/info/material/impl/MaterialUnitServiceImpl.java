package com.skysport.interfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.bean.info.MaterialUnitInfo;
import com.skysport.interfaces.mapper.info.material.MaterialUnitManageMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:
 * Created by zhangjh on 2015/6/25.
 */
@Service("materialUnitService")
public class MaterialUnitServiceImpl extends CommonServiceImpl<MaterialUnitInfo> implements InitializingBean {
    @Resource(name = "materialUnitManageMapper")
    private MaterialUnitManageMapper materialUnitManageMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = materialUnitManageMapper;
    }
}
