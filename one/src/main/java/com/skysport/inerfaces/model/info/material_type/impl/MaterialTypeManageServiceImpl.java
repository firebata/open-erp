package com.skysport.inerfaces.model.info.material_type.impl;

import com.skysport.inerfaces.bean.info.MaterialTypeInfo;
import com.skysport.inerfaces.mapper.info.MaterialTypeManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("materialTypeManageService")
public class MaterialTypeManageServiceImpl extends CommonServiceImpl<MaterialTypeInfo> implements InitializingBean {
    @Resource(name = "materialTypeManageDao")
    private MaterialTypeManageMapper materialTypeManageDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = materialTypeManageDao;
    }
}
