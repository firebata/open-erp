package com.skysport.interfaces.model.develop.accessories.service;

import com.skysport.interfaces.bean.develop.AccessoriesInfo;
import com.skysport.interfaces.bean.develop.BomInfo;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/9/22.
 */
public interface IAccessoriesService extends ICommonService<AccessoriesInfo> {

    List<AccessoriesInfo> updateOrAddBatch(BomInfo bomInfo);

    List<AccessoriesInfo> queryAccessoriesList(String bomId);

    List<AccessoriesInfo> queryAllAccessoriesByBomId(String bomId);
}

