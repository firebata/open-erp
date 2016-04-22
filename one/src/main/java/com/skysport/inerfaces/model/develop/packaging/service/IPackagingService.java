package com.skysport.inerfaces.model.develop.packaging.service;

import com.skysport.core.model.common.ICommonService;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.PackagingInfo;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/24.
 */
public interface IPackagingService extends ICommonService<PackagingInfo> {

    List<PackagingInfo> updateOrAddBatch(BomInfo bomInfo);

    List<PackagingInfo> queryPackagingList(String bomId);

    List<PackagingInfo> queryPackagingByBomId(String bomId);
}
