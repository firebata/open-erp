package com.skysport.interfaces.model.info.sex.impl;

import com.skysport.interfaces.bean.info.SexInfo;
import com.skysport.interfaces.mapper.info.SexMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("sexManageService")
public class SexManageServiceImpl extends CommonServiceImpl<SexInfo> implements InitializingBean {
    @Resource(name = "sexMapper")
    private SexMapper sexMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = sexMapper;
    }
}
