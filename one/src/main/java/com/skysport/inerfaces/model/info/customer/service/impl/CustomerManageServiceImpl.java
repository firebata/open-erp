package com.skysport.inerfaces.model.info.customer.service.impl;

import com.skysport.inerfaces.bean.info.CustomerInfo;
import com.skysport.inerfaces.mapper.info.CustomerManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/3.
 */
@Service("customerManageService")
public class CustomerManageServiceImpl extends CommonServiceImpl<CustomerInfo> implements InitializingBean {
    @Resource(name = "customerManageDao")
    private CustomerManageMapper customerManageDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = customerManageDao;
    }
}
