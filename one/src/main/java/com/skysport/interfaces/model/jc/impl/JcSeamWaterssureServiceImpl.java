package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcSeamWaterssure;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcSeamWaterssureMapper;
import com.skysport.interfaces.model.jc.IJcSeamWaterssureService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-7 15:10:10
 */
@Service("jcSeamWaterssureServiceImpl")
public class JcSeamWaterssureServiceImpl extends CommonServiceImpl<JcSeamWaterssure> implements IJcSeamWaterssureService, InitializingBean {
    @Resource(name = "jcSeamWaterssureMapper")
    private JcSeamWaterssureMapper jcSeamWaterssureMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Override
    public void afterPropertiesSet() {
        commonMapper = jcSeamWaterssureMapper;
    }

    @Override
    public List<JcSeamWaterssure> searchInfos(DataTablesInfo info) {
        List<JcSeamWaterssure> resut = null;
        resut = super.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcSeamWaterssure info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_SEAM_WATERSSURE, currentNo, incrementNumberService));
        jcSeamWaterssureMapper.add(info);
    }

    @Override
    public void edit(JcSeamWaterssure info) {
        jcSeamWaterssureMapper.updateInfo(info);
    }

    @Override
    public JcSeamWaterssure queryInfoByNatrualKey(String natrualKey) {
        return jcSeamWaterssureMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        super.del(natrualKey);
    }

}