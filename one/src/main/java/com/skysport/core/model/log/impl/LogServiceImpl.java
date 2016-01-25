package com.skysport.core.model.log.impl;

import com.skysport.core.bean.Log;
import com.skysport.core.mapper.LogInfoMapper;
import com.skysport.core.model.log.LogService;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 说明:
 * Created by zhangjh on 2016/1/11.
 */
@Service("logService")
public class LogServiceImpl extends CommonServiceImpl<Log> implements LogService, InitializingBean {
    @Resource(name = "logInfoMapper")
    private LogInfoMapper logInfoMapper;

//    @Override
//    public void add(Log log) {
//
//    }

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = logInfoMapper;
    }
}
