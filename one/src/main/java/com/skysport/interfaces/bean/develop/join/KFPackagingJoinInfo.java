package com.skysport.interfaces.bean.develop.join;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.interfaces.bean.develop.PackagingInfo;
import com.skysport.interfaces.bean.develop.MaterialSpInfo;
import com.skysport.interfaces.bean.develop.MaterialUnitDosage;

/**
 * 说明:
 * Created by zhangjh on 2015/9/23.
 */
public class KFPackagingJoinInfo extends SelectItem2 {

    private PackagingInfo packagingInfo;
    private MaterialSpInfo materialSpInfo;
    private MaterialUnitDosage materialUnitDosage;

    public PackagingInfo getPackagingInfo() {
        return packagingInfo;
    }

    public void setPackagingInfo(PackagingInfo packagingInfo) {
        this.packagingInfo = packagingInfo;
    }

    public MaterialSpInfo getMaterialSpInfo() {
        return materialSpInfo;
    }

    public void setMaterialSpInfo(MaterialSpInfo materialSpInfo) {
        this.materialSpInfo = materialSpInfo;
    }

    public MaterialUnitDosage getMaterialUnitDosage() {
        return materialUnitDosage;
    }

    public void setMaterialUnitDosage(MaterialUnitDosage materialUnitDosage) {
        this.materialUnitDosage = materialUnitDosage;
    }
}
