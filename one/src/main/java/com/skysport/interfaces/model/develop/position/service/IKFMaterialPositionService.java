package com.skysport.interfaces.model.develop.position.service;

import com.skysport.interfaces.bean.develop.KFMaterialPosition;
import com.skysport.core.model.common.ICommonService;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/10.
 */
public interface IKFMaterialPositionService extends ICommonService<KFMaterialPosition> {

    void addBatch(List<KFMaterialPosition> infos, String materialId);
}
