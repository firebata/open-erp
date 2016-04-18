package com.skysport.inerfaces.model.develop.accessories.service;

import com.skysport.inerfaces.bean.develop.AccessoriesInfo;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.join.AccessoriesJoinInfo;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/22.
 */
public interface IAccessoriesService extends ICommonService<AccessoriesInfo> {

    List<AccessoriesInfo> updateOrAddBatch(List<AccessoriesJoinInfo> accessoriesItems, BomInfo bomInfo);

    List<AccessoriesInfo> queryAccessoriesList(String bomId);

    List<AccessoriesInfo> queryAllAccessoriesByBomId(String bomId);
}

