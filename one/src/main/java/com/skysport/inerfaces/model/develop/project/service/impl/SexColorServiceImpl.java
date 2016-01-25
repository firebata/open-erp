package com.skysport.inerfaces.model.develop.project.service.impl;

import com.skysport.inerfaces.bean.develop.SexColor;
import com.skysport.inerfaces.mapper.info.SexColorManageMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.project.service.ISexColorService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/11/11.
 */
@Service("sexColorService")
public class SexColorServiceImpl extends CommonServiceImpl<SexColor> implements ISexColorService, InitializingBean {

    @Resource(name = "sexColorManageMapper")
    private SexColorManageMapper sexColorManageMapper;

    @Override
    public void afterPropertiesSet() {
        commonDao = sexColorManageMapper;
    }

    @Override
    public List<SexColor> searchInfos2(String natrualKey) {
        return sexColorManageMapper.searchInfos2(natrualKey);
    }
}
