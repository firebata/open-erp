package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcWaterProof;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcWaterProofMapper;
import com.skysport.interfaces.model.jc.IJcWaterProofService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-5 9:26:40
 */
@Service("jcWaterProofServiceImpl")
public class JcWaterProofServiceImpl extends CommonServiceImpl<JcWaterProof> implements IJcWaterProofService, InitializingBean {
    @Resource(name = "jcWaterProofMapper")
    private JcWaterProofMapper jcWaterProofMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;


    @Override
    public void afterPropertiesSet() {
        commonMapper = jcWaterProofMapper;
    }

    @Override
    public List<JcWaterProof> searchInfos(DataTablesInfo info) {
        List<JcWaterProof> resut = null;
        resut = jcWaterProofMapper.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcWaterProof info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_WATER_PROOF, currentNo, incrementNumberService));
        super.add(info);
    }

    @Override
    public void edit(JcWaterProof info) {
        jcWaterProofMapper.updateInfo(info);
    }

    @Override
    public JcWaterProof queryInfoByNatrualKey(String natrualKey) {
        return jcWaterProofMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        jcWaterProofMapper.del(natrualKey);
    }

}