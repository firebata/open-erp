package com.skysport.interfaces.model.info.series.impl;

import com.skysport.interfaces.bean.info.SeriesInfo;
import com.skysport.interfaces.mapper.info.SeriesMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by zhangjh on 2015/6/16.
 */
@Service("seriesManageService")
public class SeriesManageServiceImpl extends CommonServiceImpl<SeriesInfo> implements InitializingBean {
    @Resource(name = "seriesMapper")
    private SeriesMapper seriesMapper;

    @Override
    public void afterPropertiesSet() {
        commonMapper = seriesMapper;
    }
}
