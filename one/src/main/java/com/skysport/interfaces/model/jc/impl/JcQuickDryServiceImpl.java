package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcQuickDry;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcQuickDryMapper;
import com.skysport.interfaces.model.jc.IJcQuickDryService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-7 14:06:47
 */
@Service("jcQuickDryServiceImpl")
public class JcQuickDryServiceImpl extends CommonServiceImpl<JcQuickDry> implements IJcQuickDryService, InitializingBean {
    @Resource(name = "jcQuickDryMapper")
    private JcQuickDryMapper jcQuickDryMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Override
    public void afterPropertiesSet() {
        commonMapper = jcQuickDryMapper;
    }

    @Override
    public List<JcQuickDry> searchInfos(DataTablesInfo info) {
        List<JcQuickDry> resut = null;
        resut = super.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcQuickDry info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_QUICK_DRY, currentNo, incrementNumberService));
        jcQuickDryMapper.add(info);
    }

    @Override
    public void edit(JcQuickDry info) {
        jcQuickDryMapper.updateInfo(info);
    }

    @Override
    public JcQuickDry queryInfoByNatrualKey(String natrualKey) {
        return jcQuickDryMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        super.del(natrualKey);
    }

}