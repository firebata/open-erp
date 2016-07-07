package com.skysport.interfaces.model.permission.resource.service.impl;

import com.skysport.core.bean.permission.ResourceInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.interfaces.mapper.permission.ResourceInfoMapper;
import com.skysport.interfaces.model.permission.resource.service.IResourceInfoService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 说明:
 * Created by zhangjh on 2015/11/19.
 */
@Service("resourceInfoService")
public class ResourceInfoServiceImpl extends CommonServiceImpl<ResourceInfo> implements IResourceInfoService, InitializingBean {

    @Resource(name = "resourceInfoMapper")
    private ResourceInfoMapper resourceInfoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonMapper = resourceInfoMapper;
    }


}
