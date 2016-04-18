package com.skysport.inerfaces.model.develop.packaging.service;

import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.KFPackaging;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
public interface IPackagingService extends ICommonService<KFPackaging> {

    List<KFPackaging> updateOrAddBatch(BomInfo bomInfo);

    List<KFPackaging> queryPackagingList(String bomId);

    List<KFPackaging> queryPackagingByBomId(String bomId);
}
