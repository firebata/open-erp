package com.skysport.inerfaces.model.develop.pantone.helper;

import com.skysport.core.constant.CharConstant;
import com.skysport.inerfaces.bean.develop.KFMaterialPantone;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/1/20.
 */
public enum MaterialPantoneServiceHelper {
    SINGLETONE;

    /**
     * 设置物料位置的物料id
     *
     * @param pantones
     * @param materialId
     */
    public void setPantoneFabricId(List<KFMaterialPantone> pantones, String materialId) {
        if (null == pantones || pantones.size() > 0) {
            for (KFMaterialPantone kfMaterialPantone : pantones) {
                kfMaterialPantone.setMaterialId(materialId);
            }
        }

    }

    /**
     * @param kfMaterialPantones
     * @return
     */
    public String turnIdsToNames(List<KFMaterialPantone> kfMaterialPantones) {
        StringBuilder patones = new StringBuilder();
//        List<SelectItem2> selectItem2s = SystemBaseInfoCachedMap.SINGLETONE.popBom("patonesItems");
//        if (null == selectItem2s || selectItem2s.isEmpty()) {
//            return patones.toString();
//        }
        for (int idx = 0, len = kfMaterialPantones.size(); idx < len; idx++) {
            KFMaterialPantone pantone = kfMaterialPantones.get(idx);
//            String pantoneId = pantone.getPantoneId();
            String pantoneName = pantone.getPantoneName();
//            String pantoneName = SystemBaseInfoCachedMap.SINGLETONE.getName(selectItem2s, pantoneId);
            if (idx == 0) {
                patones.append(pantoneName);
            } else {
                patones.append(CharConstant.FORWARD_SLASH).append(pantoneName);
            }
        }
        return patones.toString();
    }

    /**
     *
     * @param compositePantoneIds 更改面料颜色所属：面布或者底布
     * @param type 0：面布；1：底布
     */
    public void changeFabricPantoneType(List<KFMaterialPantone> compositePantoneIds, String materialId, String type) {
        for(KFMaterialPantone pantone:compositePantoneIds){
            pantone.setType(type);
            pantone.setMaterialId(materialId);
        }
    }
}
