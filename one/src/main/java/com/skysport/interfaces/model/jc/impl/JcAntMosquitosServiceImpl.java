package com.skysport.interfaces.model.jc.impl;

import com.skysport.core.bean.page.DataTablesInfo;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.model.seqno.service.IncrementNumberService;
import com.skysport.interfaces.bean.jc.JcAntMosquitos;
import com.skysport.interfaces.constant.WebConstants;
import com.skysport.interfaces.mapper.jc.JcAntMosquitosMapper;
import com.skysport.interfaces.model.jc.IJcAntMosquitosService;
import com.skysport.interfaces.utils.BuildSeqNoHelper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类说明:
 * Created by zhangjh on 2016-7-7 13:46:28
 */
@Service("jcAntMosquitosServiceImpl")
public class JcAntMosquitosServiceImpl extends CommonServiceImpl<JcAntMosquitos> implements IJcAntMosquitosService, InitializingBean {
    @Resource(name = "jcAntMosquitosMapper")
    private JcAntMosquitosMapper jcAntMosquitosMapper;

    @Resource(name = "incrementNumber")
    private IncrementNumberService incrementNumberService;

    @Override
    public void afterPropertiesSet() {
        commonMapper = jcAntMosquitosMapper;
    }

    @Override
    public List<JcAntMosquitos> searchInfos(DataTablesInfo info) {
        List<JcAntMosquitos> resut = null;
        resut = super.searchInfos(info);
        return resut;
    }


    @Override
    public void add(JcAntMosquitos info) {
        String currentNo = queryCurrentSeqNo();
        //设置ID
        info.setNatrualkey(BuildSeqNoHelper.SINGLETONE.getNextSeqNo(WebConstants.T_JC_ANT_MOSQUITOS, currentNo, incrementNumberService));
        jcAntMosquitosMapper.add(info);
    }

    @Override
    public void edit(JcAntMosquitos info) {
        jcAntMosquitosMapper.updateInfo(info);
    }

    @Override
    public JcAntMosquitos queryInfoByNatrualKey(String natrualKey) {
        return jcAntMosquitosMapper.queryInfo(natrualKey);
    }

    @Override
    public void del(String natrualKey) {
        super.del(natrualKey);
    }

}