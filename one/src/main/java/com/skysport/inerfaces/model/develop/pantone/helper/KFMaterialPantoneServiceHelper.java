package com.skysport.inerfaces.model.develop.pantone.helper;

import com.skysport.inerfaces.bean.develop.KFMaterialPantone;

import java.util.List;

/**
 * 说明:
 * Created by zhangjh on 2016/1/20.
 */
public enum KFMaterialPantoneServiceHelper {
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
}
