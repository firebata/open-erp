package com.skysport.inerfaces.model.develop.position.helper;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.cache.SystemBaseInfoCachedMap;
import com.skysport.inerfaces.bean.develop.KFMaterialPosition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public String turnIdsToNames(List<KFMaterialPosition> kfMaterialPositions) {

        StringBuilder positions = new StringBuilder();
        List<SelectItem2> selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("positionItems");
        if (selectItem2s.isEmpty()) {
            return positions.toString();
        }
        Set<String> positionIds = new HashSet<>();
        for (int idx = 0, len = kfMaterialPositions.size(); idx < len; idx++) {
            KFMaterialPosition position = kfMaterialPositions.get(idx);
            String positionId = position.getPositionId();
            positionIds.add(positionId);
        }
        int idx = 0;
        for (String positionId : positionIds) {
            String positionName = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, positionId);
            if (idx == 0) {
                positions.append(positionName);
            } else {
                positions.append(CharConstant.FORWARD_SLASH).append(positionName);
            }
            idx++;
        }

        return positions.toString();
    }
}
