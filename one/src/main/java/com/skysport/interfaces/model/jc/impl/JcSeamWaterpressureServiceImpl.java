package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcSeamWaterpressure;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcSeamWaterpressureMapper;
import com.skysport.interfaces.model.jc.IJcSeamWaterpressureService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-11 11:54:27
 */
@Service("jcSeamWaterpressureServiceImpl")
public class JcSeamWaterpressureServiceImpl extends CommonServiceImpl<JcSeamWaterpressure> implements IJcSeamWaterpressureService, InitializingBean {
    @Resource(name = "jcSeamWaterpressureMapper")
    private JcSeamWaterpressureMapper jcSeamWaterpressureMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Override
    public void afterPropertiesSet() {
        commonMapper = jcSeamWaterpressureMapper;
    }

    @Override
    public List<JcSeamWaterpressure> searchInfos(DataTablesInfo info) {
        List<JcSeamWaterpressure> resut = null;
        resut = super.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcSeamWaterpressure info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_SEAM_WATERPRESSURE, currentNo, incrementNumberService));
        jcSeamWaterpressureMapper.add(info);
    }

    @Override
    public void edit(JcSeamWaterpressure info) {
        jcSeamWaterpressureMapper.updateInfo(info);
    }

    @Override
    public JcSeamWaterpressure queryInfoByNatrualKey(String natrualKey) {
        return jcSeamWaterpressureMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        super.del(natrualKey);
    }

}