package com.skysport.inerfaces.model.info.material.impl;

import com.skysport.inerfaces.bean.info.BondingLaminationCoatingInfo;
import com.skysport.inerfaces.mapper.info.material.BondingLaminationCoatingDao;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * 类说明:复合或涂层
 * Created by zhangjh on 2015/6/25.
 */
@Component("bondingLaminationCoatingService")
public class BondingLaminationCoatingServiceImpl extends CommonServiceImpl<BondingLaminationCoatingInfo> implements InitializingBean {
    @Resource(name = "bondingLaminationCoatingDao")
    private BondingLaminationCoatingDao bondingLaminationCoatingDao;

    @Override
    public void afterPropertiesSet()  {
        commonDao = bondingLaminationCoatingDao;
    }
}
