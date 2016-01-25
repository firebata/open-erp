package com.skysport.inerfaces.bean.develop.join;

import com.skysport.core.bean.system.SelectItem2;
import com.skysport.inerfaces.bean.develop.KFPackaging;
import com.skysport.inerfaces.bean.develop.MaterialSpInfo;
import com.skysport.inerfaces.bean.develop.MaterialUnitDosage;

/**
 * 说明:
 * Created by zhangjh on 2015/9/23.
 */
public class KFPackagingJoinInfo extends SelectItem2 {

    private KFPackaging kfPackaging;
    private MaterialSpInfo materialSpInfo;
    private MaterialUnitDosage materialUnitDosage;

    public KFPackaging getKfPackaging() {
        return kfPackaging;
    }

    public void setKfPackaging(KFPackaging kfPackaging) {
        this.kfPackaging = kfPackaging;
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
