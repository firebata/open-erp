package com.skysport.inerfaces.model.develop.quoted.service.impl;

import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.utils.UuidGeneratorUtils;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.inerfaces.mapper.develop.FactoryQuotedInfoMapper;
import com.skysport.inerfaces.model.develop.quoted.service.IFactoryQuoteService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 说明:
 * Created by zhangjh on 2015/10/8.
 */
@Service("factoryQuoteService")
public class FactoryQuoteServiceImpl extends CommonServiceImpl<FactoryQuoteInfo> implements IFactoryQuoteService, InitializingBean {


    @Resource(name = "factoryQuotedInfoMapper")
    private FactoryQuotedInfoMapper factoryQuotedInfoMapper;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = factoryQuotedInfoMapper;
    }

    @Override
    public List<FactoryQuoteInfo> queryFactoryQuoteInfoList(String bomId) {

        List<FactoryQuoteInfo> factoryQuoteInfos = factoryQuotedInfoMapper.queryFactoryQuoteInfoList(bomId);

        return factoryQuoteInfos;
    }

    @Override
    public List<FactoryQuoteInfo> updateOrAddBatch(BomInfo bomInfo) {
        List<FactoryQuoteInfo> factoryQuoteInfos = bomInfo.getFactoryQuoteInfos();
        List<FactoryQuoteInfo> factorysRtn = new ArrayList<>();
        String bomId = StringUtils.isBlank(bomInfo.getNatrualkey()) ? bomInfo.getBomId() : bomInfo.getNatrualkey();

        //找出被删除的工厂报价id，并删除
        deleteFactoryQuoteInfoByIds(factoryQuoteInfos, bomId);

        if (null != factoryQuoteInfos) {
            //工厂报价id存在，修改；工厂报价id不存在则新增
            for (FactoryQuoteInfo factoryQuoteInfo : factoryQuoteInfos) {

                factoryQuoteInfo.setBomId(bomId);
                String quoteInfoId = factoryQuoteInfo.getFactoryQuoteId();

                //有id，更新
                if (StringUtils.isNotBlank(quoteInfoId)) {
                    //项目信息
                    factoryQuotedInfoMapper.updateInfo(factoryQuoteInfo);
                }
                //无id，新增
                else {

                    quoteInfoId = UuidGeneratorUtils.getNextId();

                    factoryQuoteInfo.setFactoryQuoteId(quoteInfoId);

                    factoryQuotedInfoMapper.add(factoryQuoteInfo);
                }
                factorysRtn.add(factoryQuoteInfo);
            }
        }
        return factorysRtn;
    }


//    /**
//     * @param bomInfo
//     * @param factoryQuoteInfo
//     * @return
//     */
//    private String buildKindName(BomInfo bomInfo, FactoryQuoteInfo factoryQuoteInfo) {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(bomInfo.getBomId());
//        stringBuilder.append(WebConstants.CLOTH_MATERIAL_TYPE_ID);
//        stringBuilder.append(factoryQuoteInfo.getNameNum());
//        return stringBuilder.toString();
//    }

    /**
     * @param factoryQuoteInfos
     * @param bomId
     */
    private void deleteFactoryQuoteInfoByIds(List<FactoryQuoteInfo> factoryQuoteInfos, String bomId) {
        List<String> allFactoryQuoteIds = factoryQuotedInfoMapper.selectAlId(bomId);

        List<String> needToSavIds = buildNeedSaveId(factoryQuoteInfos);

        allFactoryQuoteIds.removeAll(needToSavIds);

        if (null != allFactoryQuoteIds && !allFactoryQuoteIds.isEmpty()) {
            factoryQuotedInfoMapper.deleteByIds(allFactoryQuoteIds);
        }

    }

    /**
     * @param factoryQuoteInfos
     * @return
     */
    private List<String> buildNeedSaveId(List<FactoryQuoteInfo> factoryQuoteInfos) {

        List<String> needToSavIds = new ArrayList<>();

        if (null != factoryQuoteInfos) {
            //工厂报价id存在，修改；工厂报价id不存在则新增
            for (FactoryQuoteInfo factoryQuoteInfo : factoryQuoteInfos) {
                needToSavIds.add(factoryQuoteInfo.getFactoryQuoteId());
            }
        }

        return needToSavIds;
    }

    @Override
    public void updateApproveStatus(String businessKey, String status) {

    }

    @Override
    public void updateApproveStatusBatch(List<String> businessKeys, String status) {

    }

    @Override
    public void submit(String businessKey) {

    }

    @Override
    public void submit(String taskId, String businessKey) {

    }

    @Override
    public List<ProcessInstance> queryProcessInstancesActiveByBusinessKey(String natrualKey) {
        return null;
    }

    @Override
    public List<ProcessInstance> queryProcessInstancesSuspendedByBusinessKey(String natrualKey) {
        return null;
    }

    @Override
    public Map<String, Object> getVariableOfTaskNeeding(boolean approve) {
        return null;
    }
}
