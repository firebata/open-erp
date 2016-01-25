package com.skysport.inerfaces.model.develop.position.helper;

import com.skysport.inerfaces.bean.develop.KFMaterialPosition;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2015/10/12.
 */
public enum KFMaterialPositionServiceHelper {
    SINGLETONE;

    /**
     * 设置物料位置的物料id
     *
     * @param positionIds
     * @param materialId
     */
    public void setPositionFabricId(List<KFMaterialPosition> positionIds, String materialId) {
        if (null == positionIds || positionIds.size() > 0) {
            for (KFMaterialPosition kFMaterialPosition : positionIds) {
                kFMaterialPosition.setMaterialId(materialId);
            }
        }

    }
}
