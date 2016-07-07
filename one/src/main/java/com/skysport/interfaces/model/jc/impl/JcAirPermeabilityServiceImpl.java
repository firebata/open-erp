package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcAirPermeability;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcAirPermeabilityMapper;
import com.skysport.interfaces.model.jc.IJcAirPermeabilityService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-7 15:15:48
 */
@Service("jcAirPermeabilityServiceImpl")
public class JcAirPermeabilityServiceImpl extends CommonServiceImpl<JcAirPermeability> implements IJcAirPermeabilityService, InitializingBean {
    @Resource(name = "jcAirPermeabilityMapper")
    private JcAirPermeabilityMapper jcAirPermeabilityMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Override
    public void afterPropertiesSet() {
        commonMapper = jcAirPermeabilityMapper;
    }

    @Override
    public List<JcAirPermeability> searchInfos(DataTablesInfo info) {
        List<JcAirPermeability> resut = null;
        resut = super.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcAirPermeability info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_AIR_PERMEABILITY, currentNo, incrementNumberService));
        jcAirPermeabilityMapper.add(info);
    }

    @Override
    public void edit(JcAirPermeability info) {
        jcAirPermeabilityMapper.updateInfo(info);
    }

    @Override
    public JcAirPermeability queryInfoByNatrualKey(String natrualKey) {
        return jcAirPermeabilityMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        super.del(natrualKey);
    }

}