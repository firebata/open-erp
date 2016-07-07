package com.skysport.interfaces.model.info.year_conf.impl;

import com.skysport.interfaces.bean.info.YearConfInfo;
import com.skysport.interfaces.mapper.info.YearConfMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("yearConfManageService")
public class YearConfManageServiceImpl extends CommonServiceImpl<YearConfInfo> implements InitializingBean {
    @Resource(name = "yearConfMapper")
    private YearConfMapper yearConfMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = yearConfMapper;
    }
}
