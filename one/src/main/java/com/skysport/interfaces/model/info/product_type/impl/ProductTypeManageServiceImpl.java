package com.skysport.interfaces.model.info.product_type.impl;

import com.skysport.interfaces.bean.info.ProductTypeInfo;
import com.skysport.interfaces.mapper.info.ProductTypeMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("productTypeManageService")
public class ProductTypeManageServiceImpl extends CommonServiceImpl<ProductTypeInfo> implements InitializingBean {
    @Resource(name = "productTypeMapper")
    private ProductTypeMapper productTypeMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = productTypeMapper;
    }
}
