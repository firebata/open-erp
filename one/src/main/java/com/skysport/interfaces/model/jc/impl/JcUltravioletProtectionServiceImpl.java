package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcUltravioletProtection;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcUltravioletProtectionMapper;
import com.skysport.interfaces.model.jc.IJcUltravioletProtectionService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-7 14:31:32
 */
@Service("jcUltravioletProtectionServiceImpl")
public class JcUltravioletProtectionServiceImpl extends CommonServiceImpl<JcUltravioletProtection> implements IJcUltravioletProtectionService, InitializingBean {
    @Resource(name = "jcUltravioletProtectionMapper")
    private JcUltravioletProtectionMapper jcUltravioletProtectionMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Override
    public void afterPropertiesSet() {
        commonMapper = jcUltravioletProtectionMapper;
    }

    @Override
    public List<JcUltravioletProtection> searchInfos(DataTablesInfo info) {
        List<JcUltravioletProtection> resut = null;
        resut = super.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcUltravioletProtection info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_ULTRAVIOLET_PROTECTION, currentNo, incrementNumberService));
        jcUltravioletProtectionMapper.add(info);
    }

    @Override
    public void edit(JcUltravioletProtection info) {
        jcUltravioletProtectionMapper.updateInfo(info);
    }

    @Override
    public JcUltravioletProtection queryInfoByNatrualKey(String natrualKey) {
        return jcUltravioletProtectionMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        super.del(natrualKey);
    }

}