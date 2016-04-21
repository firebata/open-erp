package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.bean.info.FinishInfo;
import com.skysport.inerfaces.mapper.info.material.FinishDao;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 类说明:后整理
 * Created by zhangjh on 2015/6/25.
 */
@Service("finishService")
public class FinishServiceImpl  extends CommonServiceImpl<FinishInfo> implements InitializingBean {
    @Resource(name = "finishDao")
    private FinishDao finishDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = finishDao;
    }
}
