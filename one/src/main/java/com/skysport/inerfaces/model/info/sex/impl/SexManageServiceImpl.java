package com.skysport.inerfaces.model.info.sex.impl;

import com.skysport.inerfaces.bean.info.SexInfo;
import com.skysport.inerfaces.mapper.info.SexManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
/**
 * Created by zhangjh on 2015/6/9.
 */
@Service("sexManageService")
public class SexManageServiceImpl extends CommonServiceImpl<SexInfo> implements InitializingBean {
    @Resource(name = "sexManageDao")
    private SexManageMapper sexManageMapper;

    @Override
    public void afterPropertiesSet()  {
        commonDao = sexManageMapper;
    }
}
