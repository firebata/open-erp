package com.skysport.inerfaces.model.develop.quoted.service;

import com.skysport.core.model.common.IApproveService;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.FactoryQuoteInfo;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/8.
 */
public interface IFactoryQuoteService extends ICommonService<FactoryQuoteInfo>, IApproveService {


    List<FactoryQuoteInfo> queryFactoryQuoteInfoList(String bomId);

    List<FactoryQuoteInfo> updateOrAddBatch(List<FactoryQuoteInfo> factoryQuoteInfos, BomInfo bomInfo);
}
