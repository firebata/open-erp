package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.inerfaces.bean.info.FinishInfo;
import com.skysport.inerfaces.mapper.info.material.FinishDao;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类说明:后整理
 * Created by zhangjh on 2015/6/25.
 */
@Component("finishService")
public class FinishServiceImpl  extends CommonServiceImpl<FinishInfo> implements InitializingBean {
    @Resource(name = "finishDao")
    private FinishDao finishDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = finishDao;
    }
}
