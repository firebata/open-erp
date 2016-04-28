package com.skysport.inerfaces.model.info.year_conf.impl;

import com.skysport.inerfaces.bean.info.YearConfInfo;
import com.skysport.inerfaces.mapper.info.YearConfMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("yearConfManageService")
public class YearConfManageServiceImpl extends CommonServiceImpl<YearConfInfo> implements InitializingBean {
    @Resource(name = "yearConfManageDao")
    private YearConfMapper yearConfMapper;

    @Override
    public void afterPropertiesSet()  {
        commonDao = yearConfMapper;
    }
}
