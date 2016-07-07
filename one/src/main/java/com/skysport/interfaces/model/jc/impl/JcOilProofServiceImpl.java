package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcOilProof;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcOilProofMapper;
import com.skysport.interfaces.model.jc.IJcOilProofService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-7 14:01:39
 */
@Service("jcOilProofServiceImpl")
public class JcOilProofServiceImpl extends CommonServiceImpl<JcOilProof> implements IJcOilProofService, InitializingBean {
    @Resource(name = "jcOilProofMapper")
    private JcOilProofMapper jcOilProofMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Override
    public void afterPropertiesSet() {
        commonMapper = jcOilProofMapper;
    }

    @Override
    public List<JcOilProof> searchInfos(DataTablesInfo info) {
        List<JcOilProof> resut = null;
        resut = super.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcOilProof info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_OIL_PROOF, currentNo, incrementNumberService));
        jcOilProofMapper.add(info);
    }

    @Override
    public void edit(JcOilProof info) {
        jcOilProofMapper.updateInfo(info);
    }

    @Override
    public JcOilProof queryInfoByNatrualKey(String natrualKey) {
        return jcOilProofMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        super.del(natrualKey);
    }

}